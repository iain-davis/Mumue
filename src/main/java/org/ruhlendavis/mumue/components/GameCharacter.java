package org.ruhlendavis.mumue.components;

import org.ruhlendavis.mumue.importer.GlobalConstants;

public class GameCharacter extends LocatableComponent {
    long playerId = GlobalConstants.REFERENCE_UNKNOWN;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public GameCharacter withPlayerId(long playerId) {
        setPlayerId(playerId);
        return this;
    }

    @Override
    public GameCharacter withName(String name) {
        setName(name);
        return this;
    }

    @Override
    public GameCharacter withLocationId(long locationId) {
        setLocationId(locationId);
        return this;
    }

    @Override
    public GameCharacter withUniverseId(long universeId) {
        setUniverseId(universeId);
        return this;
    }
}
