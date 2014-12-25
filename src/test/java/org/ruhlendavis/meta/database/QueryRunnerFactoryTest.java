package org.ruhlendavis.meta.database;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QueryRunnerFactoryTest {
    @Mock DataSource dataSource;
    @InjectMocks QueryRunnerFactory queryRunnerFactory;

    @Test
    public void createQueryRunner() {
        QueryRunner queryRunner = queryRunnerFactory.create(dataSource);
        assertNotNull(queryRunner);
        assertThat(queryRunner, is(instanceOf(QueryRunner.class)));
    }
}
