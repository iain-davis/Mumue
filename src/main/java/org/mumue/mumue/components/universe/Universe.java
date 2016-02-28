package org.mumue.mumue.components.universe;

import org.mumue.mumue.components.GameComponent;
import org.mumue.mumue.importer.GlobalConstants;

public class Universe extends GameComponent {
    long startingSpaceId = GlobalConstants.REFERENCE_UNKNOWN;

    public long getStartingSpaceId() {
        return startingSpaceId;
    }

    public void setStartingSpaceId(long startingSpaceId) {
        this.startingSpaceId = startingSpaceId;
    }
}
