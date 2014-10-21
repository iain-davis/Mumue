package org.ruhlendavis.meta.datastore;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DataStoreTest {
    private DataStore dataStore = new DataStore();

    @Test
    public void isDataStoreReady() {
        assertTrue(dataStore.isDataStoreReady());
    }
}
