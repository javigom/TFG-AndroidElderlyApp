package com.example.call.model;

import androidx.annotation.NonNull;

import java.text.Normalizer;
import java.util.Comparator;

public class ContactComparator implements Comparator<ContactModel> {

    @Override
    public int compare(@NonNull ContactModel o1, @NonNull ContactModel o2) {

        String name1 = Normalizer.normalize(o1.getName(), Normalizer.Form.NFD).replaceFirst("[^\\p{ASCII}]", "");
        String name2 = Normalizer.normalize(o2.getName(), Normalizer.Form.NFD).replaceFirst("[^\\p{ASCII}]", "");

        return name1.compareTo(name2);
    }
}
