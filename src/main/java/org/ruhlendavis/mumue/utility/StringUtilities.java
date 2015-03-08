package org.ruhlendavis.mumue.utility;

import java.util.Collection;

public class StringUtilities {
    StringUtilities() {
    }

    public static String commaIfy(Collection<String> stringCollection, String andText) {
        if (stringCollection == null || stringCollection.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        boolean afterFirst = false;
        int count = 0;
        for (String item : stringCollection) {
            count++;
            if (afterFirst) {
                if (stringCollection.size() == 2) {
                    builder.append(" ");
                } else {
                    builder.append(", ");
                }
                if (count == stringCollection.size()) {
                    builder.append(andText).append(" ");
                }
            } else {
                afterFirst = true;
            }
            builder.append(item);
        }
        return builder.toString();
    }
}
