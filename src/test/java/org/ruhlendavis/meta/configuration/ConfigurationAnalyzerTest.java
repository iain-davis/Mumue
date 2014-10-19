package org.ruhlendavis.meta.configuration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationAnalyzerTest {
    @Mock
    private Configuration configuration;

    private ConfigurationAnalyzer analyzer = new ConfigurationAnalyzer();

    @Test
    public void portAboveOneIsValid() {
        int port = RandomUtils.nextInt(1, 65536);
        when(configuration.getPort()).thenReturn(port);
        assertTrue(analyzer.isValid(configuration));
    }

    @Test
    public void analyzeWithPortBelowOneReturnsFalse() {
        when(configuration.getPort()).thenReturn(0);
        assertFalse(analyzer.isValid(configuration));
    }

    @Test
    public void analyzeWithPortAbove65535ReturnsFalse() {
        when(configuration.getPort()).thenReturn(RandomUtils.nextInt(65536, 100000));
        assertFalse(analyzer.isValid(configuration));
    }
}
