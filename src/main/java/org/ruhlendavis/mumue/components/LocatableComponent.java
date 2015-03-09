package org.ruhlendavis.mumue.components;

import org.ruhlendavis.mumue.importer.GlobalConstants;

public abstract class LocatableComponent extends Component {
    private long locationId = GlobalConstants.REFERENCE_UNKNOWN;
    private long universeId = GlobalConstants.REFERENCE_UNKNOWN;
    private long homeLocationId = GlobalConstants.REFERENCE_UNKNOWN;

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public long getUniverseId() {
        return universeId;
    }

    public void setUniverseId(long universeId) {
        this.universeId = universeId;
    }

    public long getHomeLocationId() {
        return homeLocationId;
    }

    public void setHomeLocationId(long homeLocationId) {
        this.homeLocationId = homeLocationId;
    }
}
