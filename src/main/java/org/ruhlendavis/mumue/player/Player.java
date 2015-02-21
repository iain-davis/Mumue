package org.ruhlendavis.mumue.player;

import java.util.ArrayList;
import java.util.Collection;

import org.ruhlendavis.mumue.components.GameCharacter;
import org.ruhlendavis.mumue.components.TimestampAble;

public class Player extends TimestampAble {
    String loginId = "";
    String locale = "";
    boolean administrator = false;
    private Collection<GameCharacter> characters = new ArrayList<>();

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

    public Collection<GameCharacter> getCharacters() {
        return characters;
    }

    public void setCharacters(Collection<GameCharacter> characters) {
        this.characters = characters;
    }
}
