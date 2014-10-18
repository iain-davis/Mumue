package org.ruhlendavis.meta.commands;

import java.util.ArrayList;
import java.util.List;
import org.ruhlendavis.meta.Role;

abstract public class Command {
    private String fullCommand;
    private List<Role> allowedRoles = new ArrayList<>();

    abstract public void execute(String command, String arguments);

    public String getFullCommand() {
        return fullCommand;
    }

    public Command withFullCommand(String fullCommand) {
        this.fullCommand = fullCommand;
        return this;
    }

    public List<Role> getAllowedRoles() {
        return allowedRoles;
    }

    public Command withRoles(Role... roles) {
        for (Role role : roles) {
            allowedRoles.add(role);
        }
        return this;
    }
}
