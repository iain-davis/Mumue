package org.ruhlendavis.meta.importer;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class ImporterStageTestHelper {
    protected void addOneDatabaseItemToList(List<String> list, String id, String flags, int propLines, int codaLines) {
        addOneDatabaseItemToList(list, id, flags, propLines);
        addRandomLinesToList(list, codaLines);
    }

    protected void addOneDatabaseItemToList(List<String> list, String id, String flags, int propLines) {
        list.add("#" + id);
        addRandomLinesToList(list, 4);
        list.add(flags);
        addRandomLinesToList(list, 4);
        list.add("*Props*");
        addRandomLinesToList(list, propLines);
        list.add("*End*");
    }

    protected void addRandomLinesToList(List<String> list, int count) {
        for (int i = 0; i < count; i++) {
            list.add(RandomStringUtils.randomAlphabetic(5));
        }
    }
}
