package org.mumue.mumue.database;

import static org.mockito.Mockito.verify;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class DatabaseAccessorInitializerTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock DataSource source;
    @Mock DatabaseAccessorProvider provider;
    @InjectMocks DatabaseAccessorInitializer initializer;

    private final QueryRunner queryRunner = new QueryRunnerProvider().create(source);

    @Test
    public void a() {
        initializer.initialize();
        verify(provider).create(queryRunner);
    }
}
