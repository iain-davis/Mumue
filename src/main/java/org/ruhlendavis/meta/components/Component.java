package org.ruhlendavis.meta.components;

import java.time.Instant;

public class Component {
    private Long id = 0L;
    private String name = "";
    private String description = "";
    private Instant createdTimeStamp = Instant.now();
    private Instant usedTimeStamp = Instant.now();
    private Instant modifiedTimeStamp = Instant.now();
    private long useCount = 0;

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

    public Instant getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Instant createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public long getCreatedEpochSecond() {
        return createdTimeStamp.getEpochSecond();
    }

    public void setCreatedTimeStamp(long createdTimeStamp) {
        this.createdTimeStamp = Instant.ofEpochSecond(createdTimeStamp);
    }
    public Instant getUsedTimeStamp() {
        return usedTimeStamp;
    }

    public void setUsedTimeStamp(Instant usedTimeStamp) {
        this.usedTimeStamp = usedTimeStamp;
    }

    public long getUsedEpochSecond() {
        return usedTimeStamp.getEpochSecond();
    }

    public void setUsedTimeStamp(long usedTimeStamp) {
        this.usedTimeStamp = Instant.ofEpochSecond(usedTimeStamp);
    }
    public Instant getModifiedTimeStamp() {
        return modifiedTimeStamp;
    }

    public void setModifiedTimeStamp(Instant modifiedTimeStamp) {
        this.modifiedTimeStamp = modifiedTimeStamp;
    }

    public long getModifiedEpochSecond() {
        return modifiedTimeStamp.getEpochSecond();
    }

    public void setModifiedTimeStamp(long modifiedTimeStamp) {
        this.modifiedTimeStamp = Instant.ofEpochSecond(modifiedTimeStamp);
    }

    public long getUseCount() {
        return useCount;
    }

    public void setUseCount(long useCount) {
        this.useCount = useCount;
    }
}
