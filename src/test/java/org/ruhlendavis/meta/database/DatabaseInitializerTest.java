package org.ruhlendavis.meta.database;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseInitializerTest {
    @Mock
    DatabaseInitializerDao dao;

    @Test
    public void WithoutSchemaLoadSchemaAndDefaultData() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(dao);
        when(dao.hasSchema()).thenReturn(false);
        databaseInitializer.initialize();

        InOrder order = inOrder(dao);
        order.verify(dao).loadSchema();
        order.verify(dao).loadDefaultData();
    }

    @Test
    public void WithSchemaDoNotLoadSchema() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(dao);
        when(dao.hasSchema()).thenReturn(true);
        databaseInitializer.initialize();
        verify(dao, never()).loadSchema();
    }

    @Test
    public void WithSchemaWithoutDataLoadDefaultData() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(dao);
        when(dao.hasSchema()).thenReturn(true);
        when(dao.hasData()).thenReturn(false);
        databaseInitializer.initialize();
        verify(dao).loadDefaultData();
    }

    @Test
    public void WithDataDoNotLoadDefaultData() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer(dao);
        when(dao.hasSchema()).thenReturn(true);
        when(dao.hasData()).thenReturn(true);
        databaseInitializer.initialize();
        verify(dao, never()).loadDefaultData();
    }
}
