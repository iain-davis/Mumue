package org.ruhlendavis.mumue.components;

import java.time.Instant;

import org.ruhlendavis.mumue.importer.GlobalConstants;

public class Component {
    private Long id = GlobalConstants.REFERENCE_UNKNOWN;

    private String name = "";
    private String description = "";

    private Instant created = Instant.now();
    private Instant lastUsed = Instant.now();
    private Instant lastModified = Instant.now();
    private long useCount = 0;

    private long universeId = GlobalConstants.REFERENCE_UNKNOWN;
    private long locationId = GlobalConstants.REFERENCE_UNKNOWN;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Component withId(Long id) {
        setId(id);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public long getUseCount() {
        return useCount;
    }

    public void setUseCount(long useCount) {
        this.useCount = useCount;
    }

    public long getUniverseId() {
        return universeId;
    }

    public void setUniverseId(long universeId) {
        this.universeId = universeId;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }
}
