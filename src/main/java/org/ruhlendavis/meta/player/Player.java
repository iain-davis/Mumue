package org.ruhlendavis.meta.player;

import org.ruhlendavis.meta.importer.GlobalConstants;

public class Player {
    long id = GlobalConstants.REFERENCE_UNKNOWN;
    String loginId = "";
    String name = "";
    long locationId = GlobalConstants.REFERENCE_UNKNOWN;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
