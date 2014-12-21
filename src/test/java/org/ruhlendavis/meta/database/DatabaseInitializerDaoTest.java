package org.ruhlendavis.meta.database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import org.ruhlendavis.meta.DatabaseHelper;

public class DatabaseInitializerDaoTest {
    @Test
    public void hasSchemaReturnsTrue() {
        QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithSchema();
        DatabaseInitializerDao dao = new DatabaseInitializerDao(queryRunner);
        assertTrue(dao.hasSchema());
    }

    @Test
    public void hasSchemaReturnsFalse() {
        QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithoutSchema();
        DatabaseInitializerDao dao = new DatabaseInitializerDao(queryRunner);
        assertFalse(dao.hasSchema());
    }

    @Test
    public void hasDataReturnsTrue() {
        QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithDefaultData();
        DatabaseInitializerDao dao = new DatabaseInitializerDao(queryRunner);
        assertTrue(dao.hasData());
    }

    @Test
    public void hasDataReturnsFalse() {
        QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithSchema();
        DatabaseInitializerDao dao = new DatabaseInitializerDao(queryRunner);
        assertFalse(dao.hasData());
    }

    @Test
    public void loadSchema() {
        QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithoutSchema();
        DatabaseInitializerDao dao = new DatabaseInitializerDao(queryRunner);
        dao.loadSchema();
        assertTrue(dao.hasSchema());
    }

    @Test
    public void loadDefaultData() {
        QueryRunner queryRunner = DatabaseHelper.setupTestDatabaseWithSchema();
        DatabaseInitializerDao dao = new DatabaseInitializerDao(queryRunner);
        dao.loadDefaultData();
        assertTrue(dao.hasData());
    }
}
