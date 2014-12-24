package org.ruhlendavis.meta.database;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QueryRunnerProviderTest {
    @Mock DataSource dataSource;
    @InjectMocks QueryRunnerProvider queryRunnerProvider;

    @Test
    public void createQueryRunner() {
        QueryRunner queryRunner = queryRunnerProvider.get();
        assertNotNull(queryRunner);
        assertThat(queryRunner, is(instanceOf(QueryRunner.class)));
    }
}
