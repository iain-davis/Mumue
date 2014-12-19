package org.ruhlendavis.meta.configuration.startup;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FileConfigurationAnalyzerTest {
    @Mock
    private StartupConfiguration fileConfiguration;

    private FileConfigurationAnalyzer analyzer = new FileConfigurationAnalyzer();

    @Test
    public void portAboveOneIsValid() {
        int port = RandomUtils.nextInt(1, 65536);
        when(fileConfiguration.getTelnetPort()).thenReturn(port);
        assertTrue(analyzer.isValid(fileConfiguration));
    }

    @Test
    public void analyzeWithPortBelowOneReturnsFalse() {
        when(fileConfiguration.getTelnetPort()).thenReturn(0);
        assertFalse(analyzer.isValid(fileConfiguration));
    }

    @Test
    public void analyzeWithPortAbove65535ReturnsFalse() {
        when(fileConfiguration.getTelnetPort()).thenReturn(RandomUtils.nextInt(65536, 100000));
        assertFalse(analyzer.isValid(fileConfiguration));
    }
}
