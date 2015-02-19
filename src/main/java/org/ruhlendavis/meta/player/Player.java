package org.ruhlendavis.meta.player;

import org.ruhlendavis.meta.components.IdAble;
import org.ruhlendavis.meta.importer.GlobalConstants;

public class Player extends IdAble {
    String loginId = "";
    String name = "";
    long locationId = GlobalConstants.REFERENCE_UNKNOWN;

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
