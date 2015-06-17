package org.mumue.mumue.components.universe;

import org.mumue.mumue.components.NameableComponent;
import org.mumue.mumue.importer.GlobalConstants;

public class Universe extends NameableComponent {
    long startingSpaceId = GlobalConstants.REFERENCE_UNKNOWN;

    public long getStartingSpaceId() {
        return startingSpaceId;
    }

    public void setStartingSpaceId(long startingSpaceId) {
        this.startingSpaceId = startingSpaceId;
    }
}
