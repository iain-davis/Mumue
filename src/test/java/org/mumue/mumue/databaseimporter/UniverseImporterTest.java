package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseRepository;
import org.mumue.mumue.configuration.ComponentIdManager;
import org.mumue.mumue.database.DatabaseHelper;

public class UniverseImporterTest {
    private final UniverseRepository universeRepository = new UniverseRepository(mock(ComponentIdManager.class), DatabaseHelper.setupTestDatabaseWithSchema());
    private final UniverseImporter importer = new UniverseImporter(universeRepository);

    @Test
    public void importUniverseNameFromMuckNameProperty() {
        String name = RandomStringUtils.randomAlphabetic(13);
        Properties properties = createProperties(name, RandomStringUtils.randomNumeric(5));

        properties.setProperty("muckname", name);

        Universe universe = importer.importFrom(properties);
        assertThat(universe.getName(), equalTo(name));
    }

    @Test
    public void importStartingLocationFromPlayerStartProperty() {
        String start = RandomStringUtils.randomNumeric(5);
        Properties properties = createProperties(RandomStringUtils.randomAlphabetic(13), start);

        Universe universe = importer.importFrom(properties);
        assertThat(universe.getStartingSpaceId(), equalTo(Long.parseLong(start)));
    }

    @Test
    public void saveUniverseToRepository() {
        Properties properties = createProperties(RandomStringUtils.randomAlphabetic(13), RandomStringUtils.randomNumeric(5));

        Universe expected = importer.importFrom(properties);
        Universe universe = universeRepository.getUniverse(expected.getId());

        assertThat(universe.getName(), equalTo(expected.getName()));
    }

    private Properties createProperties(String muckName, String start) {
        Properties properties = new Properties();
        properties.setProperty("muckname", muckName);
        properties.setProperty("player_start", start);
        return properties;
    }
}