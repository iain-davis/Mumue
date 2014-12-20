package org.ruhlendavis.meta.database;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QueryRunnerFactoryTest {
    @Mock DataSource dataSource;
    private QueryRunnerFactory queryRunnerFactory = new QueryRunnerFactory();

    @Test
    public void createQueryRunner() {
        assertNotNull(queryRunnerFactory.createQueryRunner(dataSource));
    }

    @Test
    public void createQueryRunnerUsesProvidedDataSource() {
        QueryRunner queryRunner = queryRunnerFactory.createQueryRunner(dataSource);
        assertSame(dataSource, queryRunner.getDataSource());
    }
}
