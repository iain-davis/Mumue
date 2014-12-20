package org.ruhlendavis.meta.components;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.ruhlendavis.meta.components.properties.PropertyTree;
import org.ruhlendavis.meta.importer.GlobalConstants;

public class Component {
    private Long id = -1L;
    private Long reference = GlobalConstants.REFERENCE_UNKNOWN;
    private String name = "";
    private Component location;
    private List<Component> contents = new ArrayList<>();
    private String description = "";
    private Instant created = Instant.now();
    private Instant lastUsed = Instant.now();
    private long useCount = 0;
    private Instant lastModified = Instant.now();
    private PropertyTree properties = new PropertyTree();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
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

    public List<Component> getContents() {
        return contents;
    }

    public Component withId(Long reference) {
        this.reference = reference;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
