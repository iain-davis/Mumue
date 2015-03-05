package org.ruhlendavis.mumue.interpreter.commands;

import org.ruhlendavis.mumue.components.character.GameCharacter;

abstract public class Command {
    abstract public void execute(GameCharacter character, String command, String arguments);
}
