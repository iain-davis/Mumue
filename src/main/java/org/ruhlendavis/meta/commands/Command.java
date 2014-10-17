package org.ruhlendavis.meta.commands;

import java.util.ArrayList;
import java.util.List;
import org.ruhlendavis.meta.Role;

abstract public class Command {
    private String minimumPartial;
    private String fullCommand;
    private List<Role> allowedRoles = new ArrayList<>();

    abstract public void execute();

    public String getMinimumPartial() {
        return minimumPartial;
    }

    public Command withMinimumPartial(String minimumPartial) {
        this.minimumPartial = minimumPartial;
        return this;
    }

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
