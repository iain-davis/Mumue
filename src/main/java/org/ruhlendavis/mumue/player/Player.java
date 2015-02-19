package org.ruhlendavis.mumue.player;

public class Player {
    String loginId = "";
    String locale = "";

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
}
