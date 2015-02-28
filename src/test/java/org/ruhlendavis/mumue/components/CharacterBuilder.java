package org.ruhlendavis.mumue.components;

import java.time.Instant;

import org.ruhlendavis.mumue.importer.GlobalConstants;

public class CharacterBuilder {
    private long id = GlobalConstants.REFERENCE_UNKNOWN;
    private Instant created = Instant.now();
    private Instant lastModified = Instant.now();
    private Instant lastUsed = Instant.now();
    private long useCount = 0;
    private String name = "";
    private String description = "";
    private long locationId = GlobalConstants.REFERENCE_UNKNOWN;
    private long universeId = GlobalConstants.REFERENCE_UNKNOWN;
    private long playerId = GlobalConstants.REFERENCE_UNKNOWN;

    public GameCharacter build() {
        GameCharacter character = new GameCharacter();
        character.setId(id);
        character.setCreated(created);
        character.setLastModified(lastModified);
        character.setLastUsed(lastUsed);
        character.setUseCount(useCount);
        character.setName(name);
        character.setDescription(description);
        character.setLocationId(locationId);
        character.setUniverseId(universeId);
        character.setPlayerId(playerId);
        return character;
    }

    public CharacterBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public CharacterBuilder withCreated(Instant created) {
        this.created = created;
        return this;
    }

    public CharacterBuilder withLastModified(Instant lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public CharacterBuilder withLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
        return this;
    }

    public CharacterBuilder withUseCount(long useCount) {
        this.useCount = useCount;
        return this;
    }

    public CharacterBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CharacterBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public CharacterBuilder withLocationId(long locationId) {
        this.locationId = locationId;
        return this;
    }

    public CharacterBuilder withUniverseId(long universeId) {
        this.universeId = universeId;
        return this;
    }

    public CharacterBuilder withPlayerId(long playerId) {
        this.playerId = playerId;
        return this;
    }
}
