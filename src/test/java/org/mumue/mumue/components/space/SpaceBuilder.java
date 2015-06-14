package org.mumue.mumue.components.space;

import java.time.Instant;

import org.mumue.mumue.importer.GlobalConstants;

public class SpaceBuilder {
    private long id = GlobalConstants.REFERENCE_UNKNOWN;
    private Instant created = Instant.now();
    private Instant lastModified = Instant.now();
    private Instant lastUsed = Instant.now();
    private long useCount = 0;
    private String name = "";
    private String description = "";
    private long locationId = GlobalConstants.REFERENCE_UNKNOWN;
    private long universeId = GlobalConstants.REFERENCE_UNKNOWN;

    public Space build() {
        Space space = new Space();
        space.setId(id);
        space.setCreated(created);
        space.setLastModified(lastModified);
        space.setLastUsed(lastUsed);
        space.setUseCount(useCount);
        space.setName(name);
        space.setDescription(description);
        space.setLocationId(locationId);
        space.setUniverseId(universeId);
        return space;
    }

    public SpaceBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public SpaceBuilder withCreated(Instant created) {
        this.created = created;
        return this;
    }

    public SpaceBuilder withLastModified(Instant lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public SpaceBuilder withLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
        return this;
    }

    public SpaceBuilder withUseCount(long useCount) {
        this.useCount = useCount;
        return this;
    }

    public SpaceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public SpaceBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public SpaceBuilder withLocationId(long locationId) {
        this.locationId = locationId;
        return this;
    }

    public SpaceBuilder withUniverseId(long universeId) {
        this.universeId = universeId;
        return this;
    }
}
