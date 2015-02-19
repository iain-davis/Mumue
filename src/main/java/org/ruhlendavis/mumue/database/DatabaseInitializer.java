package org.ruhlendavis.mumue.database;

public class DatabaseInitializer {
    private DatabaseInitializerDao dao = new DatabaseInitializerDao();

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
