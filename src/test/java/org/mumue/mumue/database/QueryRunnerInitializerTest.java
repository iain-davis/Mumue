package org.mumue.mumue.database;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.mumue.mumue.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class QueryRunnerInitializerTest {
    @Mock Configuration configuration;
    @Mock DataSource dataSource;

    @Mock DataSourceFactory dataSourceFactory;
    @Mock QueryRunnerProvider queryRunnerProvider;

    @InjectMocks QueryRunnerInitializer queryRunnerInitializer;

    @Test
    public void queryRunnerWithDataSource() {
        when(dataSourceFactory.create(configuration)).thenReturn(dataSource);
        queryRunnerInitializer.initialize(configuration);
        verify(queryRunnerProvider).create(dataSource);
    }
}
