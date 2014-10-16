package org.ruhlendavis.meta;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MetaMainTest {
    @Mock
    private ConfigurationPrompter prompter;

    @InjectMocks
    private MetaMain main = new MetaMain();

    @Test
    public void runSetsDefaultPath() throws IOException {
        String path = "configuration.properties";
        assertEquals(path, main.getConfigurationPath());
    }

    @Test
    public void runParsesRetrievesConfigurationFromCommandLine() throws URISyntaxException {
        String path = Resources.getResource("org/ruhlendavis/meta/configuration.properties").toURI().getPath();
        main.run(new String[]{path});
        assertEquals(path, main.getConfigurationPath());
    }

    @Test
    public void runParsesRetrievesConfiguration() throws URISyntaxException {
        String path = Resources.getResource("org/ruhlendavis/meta/configuration.properties").toURI().getPath();
        main.run(new String[]{path});
        assertEquals("9999", main.getConfiguration().getPort());
    }

    @Test
    public void runHandlesBadSpecifiedPath() {
        main.run(new String[]{RandomStringUtils.randomAlphabetic(15)});
    }

    @Test
    public void runHandlesMissingConfiguration() {
        main.run(new String[]{});
        verify(prompter).run(eq(System.in), eq(System.out), any(Properties.class));
    }
}
