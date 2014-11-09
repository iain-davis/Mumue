package org.ruhlendavis.meta.datastore;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class DataStoreTest {
    @Mock private DataSourceFactory dataSourceFactory;
    @Mock private QueryRunnerFactory queryRunnerFactory;
    @Mock private QueryRunner queryRunner;
    @Mock private Configuration configuration;
    @InjectMocks private DataStore dataStore = new DataStore();

    @Test
    public void isDatabaseEmptyReturnsTrue() throws SQLException {
        DataSource dataSource = new BasicDataSource();
        when(dataSourceFactory.createDataSource(configuration)).thenReturn(dataSource);
        when(queryRunnerFactory.createQueryRunner(dataSource)).thenReturn(queryRunner);
        when(queryRunner.query(eq(SqlConstants.CHECK_CONFIGURATION_TABLE_EXISTENCE), any(ResultSetHandler.class))).thenReturn(0L);
        assertTrue(dataStore.isDatabaseEmpty(configuration));
    }

    @Test
    public void isDatabaseEmptyReturnsFalse() throws SQLException {
        DataSource dataSource = new BasicDataSource();
        when(dataSourceFactory.createDataSource(configuration)).thenReturn(dataSource);
        when(queryRunnerFactory.createQueryRunner(dataSource)).thenReturn(queryRunner);
        when(queryRunner.query(eq(SqlConstants.CHECK_CONFIGURATION_TABLE_EXISTENCE), any(ResultSetHandler.class))).thenReturn(1L);
        assertFalse(dataStore.isDatabaseEmpty(configuration));
    }

    @Test
    public void isDatabaseEmptyWithSQLExceptionReturnsFalse() throws SQLException {
        DataSource dataSource = new BasicDataSource();
        when(dataSourceFactory.createDataSource(configuration)).thenReturn(dataSource);
        when(queryRunnerFactory.createQueryRunner(dataSource)).thenReturn(queryRunner);
        when(queryRunner.query(eq(SqlConstants.CHECK_CONFIGURATION_TABLE_EXISTENCE), any(ResultSetHandler.class))).thenThrow(new SQLException());
        assertFalse(dataStore.isDatabaseEmpty(configuration));
    }

    @Test
    public void populateDatabaseExecutesScriptsInCorrectOrder() throws SQLException {
        DataSource dataSource = new BasicDataSource();
        when(dataSourceFactory.createDataSource(configuration)).thenReturn(dataSource);
        when(queryRunnerFactory.createQueryRunner(dataSource)).thenReturn(queryRunner);
        dataStore.populateDatabase(configuration);
        InOrder order = inOrder(queryRunner);
        order.verify(queryRunner).update(SqlConstants.SCHEMA_SCRIPT);
        order.verify(queryRunner).update(SqlConstants.DEFAULT_DATA_SCRIPT);
    }

    @Test
    public void populateDatabaseHandlesException() throws SQLException {
        DataSource dataSource = new BasicDataSource();
        when(dataSourceFactory.createDataSource(configuration)).thenReturn(dataSource);
        when(queryRunnerFactory.createQueryRunner(dataSource)).thenReturn(queryRunner);
        when(queryRunner.update(SqlConstants.SCHEMA_SCRIPT)).thenThrow(new SQLException());
        dataStore.populateDatabase(configuration);
    }
}
