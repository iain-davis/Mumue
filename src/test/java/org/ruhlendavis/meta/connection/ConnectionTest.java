package org.ruhlendavis.meta.connection;

import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {
    @Mock Configuration configuration;
    @InjectMocks Connection connection;

    @Before
    public void beforeEach() {
        when(configuration.isTest()).thenReturn(true);
    }

    @Test
    public void doesNotRunForeverInTestMode() {
        connection.run();
    }
}
