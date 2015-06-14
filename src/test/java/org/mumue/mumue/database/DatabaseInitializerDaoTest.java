package org.mumue.mumue.database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.mumue.mumue.acceptance.DatabaseHelper;

public class DatabaseInitializerDaoTest {
    private final DatabaseInitializerDao dao = new DatabaseInitializerDao();
    @Test
    public void hasSchemaReturnsTrue() {
        DatabaseHelper.setupTestDatabaseWithSchema();
        assertTrue(dao.hasSchema());
    }

    @Test
    public void hasSchemaReturnsFalse() {
        DatabaseHelper.setupTestDatabaseWithoutSchema();
        assertFalse(dao.hasSchema());
    }

    @Test
    public void hasDataReturnsTrue() {
        DatabaseHelper.setupTestDatabaseWithDefaultData();
        assertTrue(dao.hasData());
    }

    @Test
    public void hasDataReturnsFalse() {
        DatabaseHelper.setupTestDatabaseWithSchema();
        assertFalse(dao.hasData());
    }

    @Test
    public void loadSchema() {
        DatabaseHelper.setupTestDatabaseWithoutSchema();
        dao.loadSchema();
        assertTrue(dao.hasSchema());
    }

    @Test
    public void loadDefaultData() {
        DatabaseHelper.setupTestDatabaseWithSchema();
        dao.loadDefaultData();
        assertTrue(dao.hasData());
    }
}
