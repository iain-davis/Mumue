package org.mumue.mumue.components;

import java.time.Instant;

import org.mumue.mumue.importer.GlobalConstants;

public abstract class ComponentBase {
    private long id = GlobalConstants.REFERENCE_UNKNOWN;
    private Instant created = Instant.now();
    private Instant lastUsed = Instant.now();
    private Instant lastModified = Instant.now();
    private long useCount = 0;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public void countUse() {
        useCount++;
    }
}
