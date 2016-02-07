package org.mumue.mumue.databaseimporter;

import java.util.Properties;

import javax.inject.Inject;

import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseBuilder;
import org.mumue.mumue.components.universe.UniverseRepository;

class UniverseImporter {
    private final UniverseRepository universeRepository;

    @Inject
    public UniverseImporter(UniverseRepository universeRepository) {
        this.universeRepository = universeRepository;
    }

    public Universe importFrom(Properties properties) {
        Universe universe = new UniverseBuilder()
                .withStartingSpaceId(Long.parseLong(properties.getProperty("player_start")))
                .withName(properties.getProperty("muckname"))
                .build();
        universeRepository.save(universe);
        return universe;
    }
}
