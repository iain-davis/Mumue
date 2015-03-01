package org.ruhlendavis.mumue.components.character;

import org.ruhlendavis.mumue.components.LocatableComponent;
import org.ruhlendavis.mumue.importer.GlobalConstants;

public class GameCharacter extends LocatableComponent {
    long playerId = GlobalConstants.REFERENCE_UNKNOWN;

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
