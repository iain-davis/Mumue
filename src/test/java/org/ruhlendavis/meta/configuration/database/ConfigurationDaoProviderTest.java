package org.ruhlendavis.meta.configuration.database;

import static org.junit.Assert.assertNotNull;

import com.j256.ormlite.support.ConnectionSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationDaoProviderTest {
    @Mock ConnectionSourceProvider connectionSourceProvider;
    @Mock ConnectionSource connectionSource;
    @InjectMocks ConfigurationDaoProvider configurationDaoProvider;

    @Test
    public void getReturnsConfigurationDao() {
        ConnectionSource connectionSource = new ConnectionSourceProvider().get("jdbc:h2:mem", "a", "a");
        assertNotNull(configurationDaoProvider.get(connectionSource));
    }
}
