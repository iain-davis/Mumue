package org.ruhlendavis.meta.components;

import org.ruhlendavis.meta.GlobalConstants;
import org.ruhlendavis.meta.PropertyTree;

import java.security.cert.Extension;
import java.time.Instant;

public class Component {
    private Long id = GlobalConstants.REFERENCE_UNKNOWN;
    private String name = "";
    private Component owner;
    private String description = "";
    private Instant created = Instant.now();
    private Instant lastUsed = Instant.now();
    private Instant lastModified = Instant.now();
    private long useCount = 0;
    private PropertyTree properties = new PropertyTree();
    private Component location;

    public Component getOwner() {
        return owner;
    }

    public void setOwner(Component owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public PropertyTree getProperties() {
        return properties;
    }

    public Component getLocation() {
        return location;
    }

    public void setLocation(Component location) {
        this.location = location;
    }
}
