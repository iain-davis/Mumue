package org.ruhlendavis.mumue.components.universe;

import org.ruhlendavis.mumue.components.Component;
import org.ruhlendavis.mumue.importer.GlobalConstants;

public class Universe extends Component {
    long startingSpaceId = GlobalConstants.REFERENCE_UNKNOWN;

    public long getStartingSpaceId() {
        return startingSpaceId;
    }

    public void setStartingSpaceId(long startingSpaceId) {
        this.startingSpaceId = startingSpaceId;
    }
}
