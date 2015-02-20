package org.ruhlendavis.mumue.player;

import org.ruhlendavis.mumue.components.TimestampAble;

public class Player extends TimestampAble {
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

    public Player withLocale(String locale) {
        setLocale(locale);
        return this;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }
}
