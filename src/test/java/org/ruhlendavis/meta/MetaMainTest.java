package org.ruhlendavis.meta;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.Properties;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class MetaMainTest {
    private MetaMain main = new MetaMain();

    @Test
    public void runParsesRetrievesConfigurationFromCommandLine() {
        String path = RandomStringUtils.randomAlphabetic(13);
        main.run(new String[]{path});
        assertEquals(path, main.getConfigurationPath());
    }

    @Test
    public void runSetsDefaultPath() {
        String path = "configuration.properties";
        main.run(new String[]{});
        assertEquals(path, main.getConfigurationPath());
    }
}
