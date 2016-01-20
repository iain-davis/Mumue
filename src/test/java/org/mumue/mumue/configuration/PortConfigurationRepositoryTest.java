package org.mumue.mumue.configuration;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;

public class PortConfigurationRepositoryTest {
    public static final Random RANDOM = new Random();
    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final PortConfigurationRepository repository = new PortConfigurationRepository(database);

    @Before
    public void beforeEach() {
        database.update("delete from configuration_ports");
    }

    @Test
    public void portConfigurationsShouldNeverReturnNull() {
        assertThat(repository.getAll(), notNullValue());
    }

    @Test
    public void getAllReturnsOnePortConfiguration() {
        PortConfiguration expected = insertPortConfiguration(RANDOM.nextInt(65535), PortType.TELNET, RANDOM.nextBoolean());

        assertThat(repository.getAll().size(), equalTo(1));

        PortConfiguration configuration = repository.getAll().get(0);
        assertThat(configuration.getPort(), equalTo(expected.getPort()));
        assertThat(configuration.getType(), equalTo(expected.getType()));
        assertThat(configuration.isSupportsMenus(), equalTo(expected.isSupportsMenus()));
    }

    @Test
    public void getAllReturnsAll() {
        List<PortConfiguration> expected = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            expected.add(insertPortConfiguration(RANDOM.nextInt(65535), PortType.TELNET, RANDOM.nextBoolean()));
        }

        assertThat(repository.getAll().size(), equalTo(6));
    }

    private PortConfiguration insertPortConfiguration(int port, PortType type, boolean supportsMenus) {
        String sql = "insert into configuration_ports (port, type, supportsMenus) " +
                "values (" + port + ", '" + type + "'," + supportsMenus + ")";
        database.update(sql);
        PortConfiguration portConfiguration = new PortConfiguration();
        portConfiguration.setPort(port);
        portConfiguration.setType(type);
        portConfiguration.setSupportsMenus(supportsMenus);
        return portConfiguration;
    }

}
