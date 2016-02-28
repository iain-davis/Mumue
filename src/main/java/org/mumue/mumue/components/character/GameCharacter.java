package org.mumue.mumue.components.character;

import org.mumue.mumue.components.LocatableComponent;
import org.mumue.mumue.importer.GlobalConstants;

public class GameCharacter extends LocatableComponent {
    private long playerId = GlobalConstants.REFERENCE_UNKNOWN;
    private long homeLocationId = GlobalConstants.REFERENCE_UNKNOWN;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getHomeLocationId() {
        return homeLocationId;
    }

    public void setHomeLocationId(long homeLocationId) {
        this.homeLocationId = homeLocationId;
    }
}
