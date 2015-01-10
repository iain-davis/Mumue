package org.ruhlendavis.meta.text;

public enum TextName {
    Welcome("welcome screen"),
    LoginPrompt("login prompt"),
    PasswordPrompt("password prompt"),
    LoginFailed("login failed"),
    ;

    private final String name;

    private TextName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
