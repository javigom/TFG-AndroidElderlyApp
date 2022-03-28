package com.example.simplecall.data;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;

import androidx.annotation.NonNull;

import com.example.simplecall.model.CallModel;
import com.example.simplecall.model.ContactModel;
import com.example.simplecall.util.ContactComparator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        // Update name
        String where = String.format("%s = '%s' AND %s = ?", ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, ContactsContract.Data.CONTACT_ID);

        ops.add(ContentProviderOperation
                .newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(where, new String[]{String.valueOf(contactModel.getId())})
                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contactModel.getName())
                .build()
        );

        // Update number
        where = String.format("%s = '%s' AND %s = ?", ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, ContactsContract.Data.DATA1);

        ops.add(ContentProviderOperation
                .newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(where, new String[]{phone})
                .withValue(ContactsContract.Data.DATA1, contactModel.getPhone())
                .build()
        );


        if(contactModel.getPhoto() != null) {
            try {
                changeContactPhoto(contactModel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

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
    }

    private void changeContactPhoto(ContactModel contactModel) throws IOException {
        Uri rawContactUri = null;
        Cursor rawContactCursor =  contentResolver.query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[] {ContactsContract.RawContacts._ID},
                ContactsContract.RawContacts.CONTACT_ID + " = " + String.valueOf(contactModel.getId()),
                null,
                null);
        if(!rawContactCursor.isAfterLast()) {
            rawContactCursor.moveToFirst();
            rawContactUri = ContactsContract.RawContacts.CONTENT_URI.buildUpon().appendPath(""+rawContactCursor.getLong(0)).build();
        }
        rawContactCursor.close();

        InputStream iStream = contentResolver.openInputStream(Uri.parse(contactModel.getPhoto()));

        if(iStream != null) {
            byte[] photoByteArray = getBytes(iStream);


            ContentValues values = new ContentValues();
            int photoRow = -1;
            String where = ContactsContract.Data.RAW_CONTACT_ID + " == " +
                    ContentUris.parseId(rawContactUri) + " AND " + ContactsContract.Data.MIMETYPE + "=='" +
                    ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'";
            Cursor cursor = contentResolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    where,
                    null,
                    null);
            int idIdx = cursor.getColumnIndexOrThrow(ContactsContract.Data._ID);
            if(cursor.moveToFirst()){
                photoRow = cursor.getInt(idIdx);
            }
            cursor.close();
            values.put(ContactsContract.Data.RAW_CONTACT_ID,
                    ContentUris.parseId(rawContactUri));
            values.put(ContactsContract.Data.IS_SUPER_PRIMARY, 1);
            values.put(ContactsContract.CommonDataKinds.Photo.PHOTO, photoByteArray);
            values.put(ContactsContract.Data.MIMETYPE,
                    ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE);
            if(photoRow >= 0){
                this.contentResolver.update(
                        ContactsContract.Data.CONTENT_URI,
                        values,
                        ContactsContract.Data._ID + " = " + photoRow, null);
            } else {
                this.contentResolver.insert(
                        ContactsContract.Data.CONTENT_URI,
                        values);
            }

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

        int rawContactID = ops.size();

        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        );

        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                .build()
        );

        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build()
        );

        if(contactModel.getPhoto() != null) {

            try {
                InputStream iStream = contentResolver.openInputStream(Uri.parse(contactModel.getPhoto()));

                if(iStream != null) {
                    byte[] photoByteArray = getBytes(iStream);

                    ops.add(ContentProviderOperation
                            .newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactID)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Photo.PHOTO, photoByteArray)
                            .build()
                    );
                }

            } catch(Exception e){
                e.printStackTrace();
            }
        }

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

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

}
