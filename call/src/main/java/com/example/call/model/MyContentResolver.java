package com.example.call.model;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyContentResolver {

    private ContentResolver contentResolver;

    public MyContentResolver(ContentResolver contentResolver){
        this.contentResolver = contentResolver;
    }

    /**
     * Loads the contact data and stores them in both lists
     * @param contactModelList
     * @param favContactList
     */
    public void loadContacts(List<ContactModel> contactModelList, List<ContactModel> favContactList, Map<String, ContactModel> phoneContactMap) {

        // Order by name
        String sortOrder = ContactsContract.Data.DISPLAY_NAME + " ASC";

        // Query
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, sortOrder);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                // ID
                long id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                // HAS PHONE
                int hasPhone = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (hasPhone > 0) {
                    Cursor cursorPhone = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{String.valueOf(id)}, null);

                    if (cursorPhone != null && cursorPhone.moveToFirst()) {

                        // NAME
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        // PHONE
                        String phone = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        // IS STARRED
                        Integer isStarred = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.STARRED)));
                        // PHOTO
                        String photo = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));

                        // Contact to storage
                        ContactModel contact = new ContactModel(id, name, PhoneNumberUtils.formatNumber(phone.replace("+34", ""), "ES"), photo, isStarred);
                        contactModelList.add(contact);

                        // Favorite list
                        if(contact.getIsStarred() == 1){
                            favContactList.add(contact);
                        }

                        // Phone-Contact map
                        phoneContactMap.put(contact.getPhone(), contact);

                        cursorPhone.close();
                    }

                }

            }

            // Sort the contact list (for special characters)
            ContactComparator comparator = new ContactComparator();
            contactModelList.sort(comparator);
            favContactList.sort(comparator);

        }

        cursor.close();

    }

    /**
     *  Loads the call log data and stores them in the list
     * @param callModelList
     */
    public void loadCallLogs(List<CallModel> callModelList){

        // Order by date
        String sortOrder = CallLog.Calls.DATE + " DESC";

        // Query
        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, sortOrder);

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                callModelList.add(recoverCallModel(cursor));
            }

            cursor.close();

        }

    }

    /**
     * Load the last call log and stores it in the first position of the list
     * @param callModelList
     * @return true if an element has been added
     */
    public int updateCalls(@NonNull List<CallModel> callModelList){

        int newCalls = 0;

        // Order by date
        String sortOrder = CallLog.Calls.DATE + " DESC";

        //String where = CallLog.Calls.DATE + " == " + callModelList.get(0).getDate();

        // Query
        Cursor cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, null, null, null, sortOrder);

        if (cursor.getCount() > 0) {

            boolean fin = false;
            while (cursor.moveToNext() && !fin) {

                CallModel call = recoverCallModel(cursor);

                if(!callModelList.get(newCalls).getDate().equals(call.getDate())){
                    callModelList.add(newCalls, call);
                    newCalls = newCalls + 1;
                }
                else{
                    fin = true;
                }
            }

            cursor.close();

        }

        return newCalls;

    }

    /**
     * Recover a CallModel with a cursor
     * @param cursor
     * @return
     */
    private CallModel recoverCallModel(@NonNull Cursor cursor){

        // PHONE
        String phone = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
        // DATE
        Date date = new Date(cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE)));
        // DURATION
        String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
        // TYPE INT
        int typeInt = cursor.getInt(cursor.getColumnIndexOrThrow(CallLog.Calls.TYPE));

        // DEFAULT VALUE -> PROBLEMS WITH OTHERS LIKE BLOCKED
        String type = "INCOMING_TYPE";

        // Type to string
        switch(typeInt) {
            case CallLog.Calls.INCOMING_TYPE:
                type = "INCOMING_TYPE";
                break;
            case CallLog.Calls.OUTGOING_TYPE:
                type = "OUTGOING_TYPE";
                break;
            case CallLog.Calls.MISSED_TYPE:
                type = "MISSED_TYPE";
                break;
        }

        // Call to storage
        return new CallModel(PhoneNumberUtils.formatNumber(phone.replace("+34", ""), "ES"), date, duration, type);

    }

    public void editContact(ContactModel contactModel, String phone){

        ArrayList<ContentProviderOperation> operations = new ArrayList<>();

        // Update name
        String where = String.format("%s = '%s' AND %s = ?", ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, ContactsContract.Data.CONTACT_ID);

        operations.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(where, new String[]{String.valueOf(contactModel.getId())})
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contactModel.getName()).build());

        // Update number
        where = String.format("%s = '%s' AND %s = ?", ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, ContactsContract.Data.DATA1);

        operations.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(where, new String[]{phone})
                .withValue(ContactsContract.Data.DATA1, contactModel.getPhone())
                .build());

        // Update isStarred
        if(contactModel.getIsStarred() == 1) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ContactsContract.Contacts.STARRED, 1);
            contentResolver.update(ContactsContract.Contacts.CONTENT_URI,
                    contentValues, ContactsContract.Contacts._ID + "=" + contactModel.getId(), null);
        }
        else if(contactModel.getIsStarred() == 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ContactsContract.Contacts.STARRED, 0);
            contentResolver.update(ContactsContract.Contacts.CONTENT_URI,
                    contentValues, ContactsContract.Contacts._ID + "=" + contactModel.getId(), null);
        }

        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, operations);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteContact(ContactModel contactModel) {

        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                // ID
                long id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (contactModel.getId() == id) {
                    String lookupKey = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                    Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                    contentResolver.delete(uri, null, null);
                    break;
                }
            }
        }
    }

    public long addContact(ContactModel contactModel){

        // Guardamos nombre, número y favorito
        String name = contactModel.getName();
        String phone = contactModel.getPhone();

        // Añadimos el contacto
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null).build());

        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name).build());

        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());

        /*
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.STARRED)
                .withValue(ContactsContract.CommonDataKinds.Phone.STARRED, isStarred).build());
        */

        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Obtenemos el identificador
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null,
                ContactsContract.Contacts.DISPLAY_NAME + " = ?", new String[]{String.valueOf(name)}, null);
        long id = -1;

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                String cname = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if(cname.equals(name)){
                    Cursor cursorPhone = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.NUMBER + " = ?", new String[]{String.valueOf(phone)}, null);

                    String cphone = "";
                    if (cursorPhone != null && cursorPhone.moveToFirst()) {
                        cphone = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        cursorPhone.close();
                    }

                    if (phone.equals(cphone)) {
                        id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                        break;
                    }
                }

            }
        }
        return id;
    }

}
