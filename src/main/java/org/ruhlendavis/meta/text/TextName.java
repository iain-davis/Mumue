package org.ruhlendavis.meta.text;

public enum TextName {
    LoginPrompt("login prompt"),
    Welcome("welcome screen"),
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
