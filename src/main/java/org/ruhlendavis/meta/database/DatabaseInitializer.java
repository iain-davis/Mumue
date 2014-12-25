package org.ruhlendavis.meta.database;

public class DatabaseInitializer {
    private final DatabaseInitializerDao dao;

    public DatabaseInitializer(DatabaseInitializerDao dao) {
        this.dao = dao;
    }

    public void initialize() {
        if (dao.hasSchema()) {
            if (!dao.hasData()) {
                dao.loadDefaultData();
            }
        } else {
            dao.loadSchema();
            dao.loadDefaultData();
        }
    }
}
