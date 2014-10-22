package org.ruhlendavis.meta.interpreter.commands;

import java.util.ArrayList;
import java.util.Arrays;
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
        allowedRoles.addAll(Arrays.asList(roles));
        return this;
    }
}
