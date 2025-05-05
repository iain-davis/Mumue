package org.mumue.mumue.database;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DatabaseInitializerTest {
    private final DatabaseInitializerDao dao = mock(DatabaseInitializerDao.class);
    private final DatabaseInitializer databaseInitializer = new DatabaseInitializer(dao);

    @Test
    void WithoutSchemaLoadSchemaAndDefaultData() {
        when(dao.hasSchema()).thenReturn(false);
        databaseInitializer.initialize();

        InOrder order = inOrder(dao);
        order.verify(dao).loadSchema();
        order.verify(dao).loadDefaultData();
    }

    @Test
    void WithSchemaDoNotLoadSchema() {
        when(dao.hasSchema()).thenReturn(true);
        databaseInitializer.initialize();
        verify(dao, never()).loadSchema();
    }

    @Test
    void WithSchemaWithoutDataLoadDefaultData() {
        when(dao.hasSchema()).thenReturn(true);
        when(dao.hasData()).thenReturn(false);
        databaseInitializer.initialize();
        verify(dao).loadDefaultData();
    }

    @Test
    void WithDataDoNotLoadDefaultData() {
        when(dao.hasSchema()).thenReturn(true);
        when(dao.hasData()).thenReturn(true);
        databaseInitializer.initialize();
        verify(dao, never()).loadDefaultData();
    }
}
