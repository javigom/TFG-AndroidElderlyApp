package com.example.launcher2.util;

import androidx.annotation.NonNull;

import com.example.launcher2.model.AppModel;

import java.text.Normalizer;
import java.util.Comparator;
import java.util.Locale;

public class AppComparatorByName implements Comparator<AppModel> {

    @Override
    public int compare(@NonNull AppModel app1, @NonNull AppModel app2) {
        String name1 = Normalizer.normalize(app1.getLabel(), Normalizer.Form.NFD).replaceFirst("[^\\p{ASCII}]", "");
        String name2 = Normalizer.normalize(app2.getLabel(), Normalizer.Form.NFD).replaceFirst("[^\\p{ASCII}]", "");
        return name1.toLowerCase(Locale.ROOT).compareTo(name2.toLowerCase(Locale.ROOT));
    }
}
