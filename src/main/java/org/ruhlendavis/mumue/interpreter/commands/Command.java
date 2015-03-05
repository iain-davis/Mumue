package org.ruhlendavis.mumue.interpreter.commands;

import org.ruhlendavis.mumue.components.character.GameCharacter;

interface Command {
    public void execute(GameCharacter character, String command, String arguments);
}
