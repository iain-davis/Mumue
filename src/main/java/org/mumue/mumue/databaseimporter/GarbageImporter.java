package org.mumue.mumue.databaseimporter;

import java.util.List;

class GarbageImporter implements ComponentImporter<Garbage> {

    @Override
    public Garbage importFrom(List<String> lines) {
        return new Garbage();
    }
}
