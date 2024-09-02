package org.mumue.mumue.databaseimporter;

import java.util.Properties;

import jakarta.inject.Inject;

import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseBuilder;
import org.mumue.mumue.components.universe.UniverseRepository;
import org.mumue.mumue.configuration.ComponentIdManager;

class UniverseImporter {
    private final ComponentIdManager componentIdManager;
    private final UniverseRepository universeRepository;
    private final UniverseBuilder universeBuilder;

    @Inject
    public UniverseImporter(ComponentIdManager componentIdManager, UniverseBuilder universeBuilder, UniverseRepository universeRepository) {
        this.componentIdManager = componentIdManager;
        this.universeBuilder = universeBuilder;
        this.universeRepository = universeRepository;
    }

    public Universe importFrom(Properties properties) {
        Universe universe = universeBuilder.withId(componentIdManager.getNewComponentId())
                .withStartingSpaceId(Long.parseLong(properties.getProperty("player_start")))
                .withName(properties.getProperty("muckname"))
                .build();
        universeRepository.add(universe);
        return universe;
    }
}
