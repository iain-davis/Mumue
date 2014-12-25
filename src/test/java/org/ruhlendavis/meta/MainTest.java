package org.ruhlendavis.meta;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;

import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.TestConstants;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfigurationFactory;
import org.ruhlendavis.meta.listener.Listener;

@RunWith(MockitoJUnitRunner.class)
public class MainTest {
    @Mock StartupConfigurationFactory startupConfigurationFactory;
    @Mock StartupConfiguration startupConfiguration;
    @Mock Listener listener;
    @InjectMocks
    Main main;

    @Before
    public void beforeEach() throws URISyntaxException {
        when(startupConfigurationFactory.create(anyString())).thenReturn(startupConfiguration);
        String path = Resources.getResource(TestConstants.TEST_CONFIGURATION_FILE_PATH).toURI().getPath();
        when(startupConfiguration.getDatabasePath()).thenReturn(path);
    }

    @Test
    public void doNotRunForeverInTest() {
        main.run(listener, "--test");
    }
}