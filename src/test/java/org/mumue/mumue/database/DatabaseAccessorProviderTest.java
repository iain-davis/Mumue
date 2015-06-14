package org.mumue.mumue.database;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseAccessorProviderTest {
    @Mock QueryRunner queryRunner;
    private final DatabaseAccessorProvider provider = new DatabaseAccessorProvider();

    @Test
    public void createQueryRunner() {
        DatabaseAccessor database = provider.create(queryRunner);
        assertNotNull(database);
        assertThat(database, is(instanceOf(DatabaseAccessor.class)));
    }

    @Test
    public void secondCallToCreateReturnsSameQueryRunner() {
        DatabaseAccessor database1 = provider.create(queryRunner);
        DatabaseAccessor database2 = provider.create(queryRunner);

        assertThat(database1, is(sameInstance(database2)));
    }

    @Test
    public void getQueryRunner() {
        provider.create(queryRunner);
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        assertNotNull(database);
        assertThat(database, is(instanceOf(DatabaseAccessor.class)));
    }

    @Test
    public void getQueryRunnerReturnsSameAsCreate() {
        DatabaseAccessor database1 = DatabaseAccessorProvider.get();
        DatabaseAccessor database2 = DatabaseAccessorProvider.get();

        assertThat(database1, is(sameInstance(database2)));
    }
}
