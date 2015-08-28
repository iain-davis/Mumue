package org.mumue.mumue.database;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DatabaseInitializerDaoTest {

    @Test
    public void hasSchemaReturnsTrue() {
        DatabaseInitializerDao dao = new DatabaseInitializerDao(DatabaseHelper.setupTestDatabaseWithSchema());
        assertTrue(dao.hasSchema());
    }

    @Test
    public void hasSchemaReturnsFalse() {
        DatabaseInitializerDao dao = new DatabaseInitializerDao(DatabaseHelper.setupTestDatabaseWithoutSchema());
        assertFalse(dao.hasSchema());
    }

    @Test
    public void hasDataReturnsTrue() {
        DatabaseInitializerDao dao = new DatabaseInitializerDao(DatabaseHelper.setupTestDatabaseWithDefaultData());
        assertTrue(dao.hasData());
    }

    @Test
    public void hasDataReturnsFalse() {
        DatabaseInitializerDao dao = new DatabaseInitializerDao(DatabaseHelper.setupTestDatabaseWithSchema());
        assertFalse(dao.hasData());
    }

    @Test
    public void loadSchema() {
        DatabaseInitializerDao dao = new DatabaseInitializerDao(DatabaseHelper.setupTestDatabaseWithoutSchema());
        dao.loadSchema();
        assertTrue(dao.hasSchema());
    }

    @Test
    public void loadDefaultData() {
        DatabaseInitializerDao dao = new DatabaseInitializerDao(DatabaseHelper.setupTestDatabaseWithSchema());
        dao.loadDefaultData();
        assertTrue(dao.hasData());
    }
}
