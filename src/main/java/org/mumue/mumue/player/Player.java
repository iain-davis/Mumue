package org.mumue.mumue.player;

import org.mumue.mumue.components.Component;

public class Player extends Component {
    private String loginId = "";
    private String locale = "";
    private boolean administrator = false;

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
