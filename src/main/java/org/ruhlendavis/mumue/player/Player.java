package org.ruhlendavis.mumue.player;

import org.ruhlendavis.mumue.components.ComponentBase;

public class Player extends ComponentBase {
    String loginId = "";
    String locale = "";
    boolean administrator = false;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }
}
