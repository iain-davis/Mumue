package org.ruhlendavis.mumue.components;

import org.ruhlendavis.mumue.importer.GlobalConstants;

public abstract class LocatableComponent extends Component {
    private long locationId = GlobalConstants.REFERENCE_UNKNOWN;
    private long universeId = GlobalConstants.REFERENCE_UNKNOWN;

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

    public LocatableComponent withLocationId(long locationId) {
        setLocationId(locationId);
        return this;
    }

    public LocatableComponent withUniverseId(long universeId) {
        setUniverseId(universeId);
        return this;
    }
}
