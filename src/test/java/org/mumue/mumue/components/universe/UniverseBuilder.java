package org.mumue.mumue.components.universe;

import java.time.Instant;

import org.mumue.mumue.importer.GlobalConstants;

public class UniverseBuilder {
    private long id = GlobalConstants.REFERENCE_UNKNOWN;
    private Instant created = Instant.now();
    private Instant lastModified = Instant.now();
    private Instant lastUsed = Instant.now();
    private long useCount = 0;
    private String name = "";
    private String description = "";
    private long startingSpaceId = GlobalConstants.REFERENCE_UNKNOWN;

    public Universe build() {
        Universe universe = new Universe();
        universe.setId(id);
        universe.setCreated(created);
        universe.setLastModified(lastModified);
        universe.setLastUsed(lastUsed);
        universe.setUseCount(useCount);
        universe.setName(name);
        universe.setDescription(description);
        universe.setStartingSpaceId(startingSpaceId);
        return universe;
    }

    public UniverseBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public UniverseBuilder withCreated(Instant created) {
        this.created = created;
        return this;
    }

    public UniverseBuilder withLastModified(Instant lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public UniverseBuilder withLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
        return this;
    }

    public UniverseBuilder withUseCount(long useCount) {
        this.useCount = useCount;
        return this;
    }

    public UniverseBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UniverseBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public UniverseBuilder withStartingSpaceId(long startingSpaceId) {
        this.startingSpaceId = startingSpaceId;
        return this;
    }
}
