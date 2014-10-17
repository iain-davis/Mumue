package org.ruhlendavis.meta.commands;

abstract public class CommandWithToken extends Command {
    private String token;

    public String getToken() {
        return token;
    }

    public Command withToken(String token) {
        this.token = token;
        return this;
    }
}
