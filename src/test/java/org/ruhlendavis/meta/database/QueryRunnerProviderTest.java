package org.ruhlendavis.meta.database;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class QueryRunnerProviderTest {
    @Mock DataSource dataSource;
    private final QueryRunnerProvider queryRunnerProvider = new QueryRunnerProvider();

    @Test
    public void createQueryRunner() {
        QueryRunner queryRunner = queryRunnerProvider.create(dataSource);
        assertNotNull(queryRunner);
        assertThat(queryRunner, is(instanceOf(QueryRunner.class)));
    }

    @Test
    public void secondCallToCreateReturnsSameQueryRunner() {
        QueryRunner queryRunner1 = queryRunnerProvider.create(dataSource);
        QueryRunner queryRunner2 = queryRunnerProvider.create(dataSource);

        assertThat(queryRunner1, is(sameInstance(queryRunner2)));
    }

    @Test
    public void getQueryRunner() {
        queryRunnerProvider.create(dataSource);
        QueryRunner queryRunner = QueryRunnerProvider.get();
        assertNotNull(queryRunner);
        assertThat(queryRunner, is(instanceOf(QueryRunner.class)));
    }

    @Test
    public void getQueryRunnerReturnsSameAsCreate() {
        QueryRunner queryRunner1 = queryRunnerProvider.create(dataSource);
        QueryRunner queryRunner2 = QueryRunnerProvider.get();

        assertThat(queryRunner1, is(sameInstance(queryRunner2)));
    }
}
