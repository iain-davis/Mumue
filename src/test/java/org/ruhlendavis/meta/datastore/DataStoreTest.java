package org.ruhlendavis.meta.datastore;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;

@RunWith(MockitoJUnitRunner.class)
public class DataStoreTest {
    private final BasicDataSource source = new BasicDataSource();
    @Mock StartupConfiguration startupConfiguration;
    @Mock DataSourceFactory dataSourceFactory;
    @Mock QueryRunnerFactory queryRunnerFactory;
    @InjectMocks private DataStore dataStore;

    @Before
    public void beforeEach() {
        source.setDriverClassName("org.h2.Driver");
        source.setUsername("user");
        source.setPassword("password");
        source.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        when(dataSourceFactory.createDataSource(startupConfiguration)).thenReturn(source);
        when(queryRunnerFactory.createQueryRunner(source)).thenCallRealMethod();
    }

    @Test
    public void isDatabaseEmptyReturnsTrue() throws SQLException {
        QueryRunner queryRunner = new QueryRunner(source);
        queryRunner.update("DROP ALL OBJECTS");
        assertTrue(dataStore.isDatabaseEmpty(startupConfiguration));
    }

    @Test
    public void isDatabaseEmptyReturnsFalse() {
        dataStore.populateDatabase(startupConfiguration);
        assertFalse(dataStore.isDatabaseEmpty(startupConfiguration));
    }

    @Test
    public void isDatabaseEmptyWithSQLExceptionReturnsFalse() throws SQLException {
        QueryRunner queryRunner = Mockito.mock(QueryRunner.class);
        when(queryRunnerFactory.createQueryRunner(source)).thenReturn(queryRunner);
        when(queryRunner.query(eq(SqlConstants.CHECK_CONFIGURATION_TABLE_EXISTENCE), any(ResultSetHandler.class))).thenThrow(new SQLException());
        assertFalse(dataStore.isDatabaseEmpty(startupConfiguration));
    }
//
//    @Test
//    public void populateDatabaseExecutesScriptsInCorrectOrder() throws SQLException {
//        DataSource dataSource = new BasicDataSource();
//        when(dataSourceFactory.createDataSource(startupConfiguration)).thenReturn(dataSource);
//        when(queryRunnerFactory.createQueryRunner(dataSource)).thenReturn(queryRunner);
//        dataStore.populateDatabase(startupConfiguration);
//        InOrder order = inOrder(queryRunner);
//        order.verify(queryRunner).update(SqlConstants.SCHEMA_SCRIPT);
//        order.verify(queryRunner).update(SqlConstants.DEFAULT_DATA_SCRIPT);
//    }
//
//    @Test
//    public void populateDatabaseHandlesException() throws SQLException {
//        DataSource dataSource = new BasicDataSource();
//        when(dataSourceFactory.createDataSource(startupConfiguration)).thenReturn(dataSource);
//        when(queryRunnerFactory.createQueryRunner(dataSource)).thenReturn(queryRunner);
//        when(queryRunner.update(SqlConstants.SCHEMA_SCRIPT)).thenThrow(new SQLException());
//        dataStore.populateDatabase(startupConfiguration);
//    }
//
//    @Test
//    public void getTextRetrievesText() throws SQLException {
//        String text = RandomStringUtils.randomAlphabetic(13);
//        String name = RandomStringUtils.randomAlphabetic(17);
//        DataSource dataSource = new BasicDataSource();
//        when(dataSourceFactory.createDataSource(startupConfiguration)).thenReturn(dataSource);
//        when(queryRunnerFactory.createQueryRunner(dataSource)).thenReturn(queryRunner);
//        when(queryRunner.query(eq(SqlConstants.QUERY_TEXT), any(ResultSetHandler.class), anyString(), eq(name))).thenReturn(text);
//        assertEquals(text, dataStore.getText(startupConfiguration, name));
//    }
//
//    @Test
//    public void getTextByLocaleRetrievesText() throws SQLException {
//        String text = RandomStringUtils.randomAlphabetic(13);
//        String locale = RandomStringUtils.randomAlphabetic(5);
//        String name = RandomStringUtils.randomAlphabetic(17);
//        DataSource dataSource = new BasicDataSource();
//        when(dataSourceFactory.createDataSource(startupConfiguration)).thenReturn(dataSource);
//        when(queryRunnerFactory.createQueryRunner(dataSource)).thenReturn(queryRunner);
//        when(queryRunner.query(eq(SqlConstants.QUERY_TEXT), any(ResultSetHandler.class), eq(locale), eq(name))).thenReturn(text);
//        assertEquals(text, dataStore.getText(startupConfiguration, locale, name));
//    }
//
//    @Test
//    public void getTextByLocaleReturnsEmptyStringOnException() throws SQLException {
//        String locale = RandomStringUtils.randomAlphabetic(5);
//        String name = RandomStringUtils.randomAlphabetic(17);
//        DataSource dataSource = new BasicDataSource();
//        when(dataSourceFactory.createDataSource(startupConfiguration)).thenReturn(dataSource);
//        when(queryRunnerFactory.createQueryRunner(dataSource)).thenReturn(queryRunner);
//        when(queryRunner.query(eq(SqlConstants.QUERY_TEXT), any(ResultSetHandler.class), eq(locale), eq(name))).thenThrow(new SQLException());
//        assertEquals("", dataStore.getText(startupConfiguration, locale, name));
//    }
}
