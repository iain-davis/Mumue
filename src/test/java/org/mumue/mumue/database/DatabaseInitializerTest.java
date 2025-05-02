package org.mumue.mumue.database;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseInitializerTest {
    @Mock DatabaseInitializerDao dao;
    @InjectMocks DatabaseInitializer databaseInitializer;

    @Test
    public void WithoutSchemaLoadSchemaAndDefaultData() {
        when(dao.hasSchema()).thenReturn(false);
        databaseInitializer.initialize();

        InOrder order = inOrder(dao);
        order.verify(dao).loadSchema();
        order.verify(dao).loadDefaultData();
    }

    @Test
    public void WithSchemaDoNotLoadSchema() {
        when(dao.hasSchema()).thenReturn(true);
        databaseInitializer.initialize();
        verify(dao, never()).loadSchema();
    }

    @Test
    public void WithSchemaWithoutDataLoadDefaultData() {
        when(dao.hasSchema()).thenReturn(true);
        when(dao.hasData()).thenReturn(false);
        databaseInitializer.initialize();
        verify(dao).loadDefaultData();
    }

    @Test
    public void WithDataDoNotLoadDefaultData() {
        when(dao.hasSchema()).thenReturn(true);
        when(dao.hasData()).thenReturn(true);
        databaseInitializer.initialize();
        verify(dao, never()).loadDefaultData();
    }
}
