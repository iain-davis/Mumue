package org.ruhlendavis.mumue.text;

public enum TextName {
    Welcome("welcome screen"),
    LoginPrompt("login prompt"),
    PasswordPrompt("password prompt"),
    LoginFailed("login failed"),
    LoginSuccess("login success"),
    PlayerMainMenu("player main menu");

    private final String name;

    private TextName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
