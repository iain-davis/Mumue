package org.ruhlendavis.mumue.interpreter.commands;

import org.ruhlendavis.mumue.components.character.GameCharacter;
import org.ruhlendavis.mumue.importer.Importer;

public class CommandImport implements Command {
    private Importer importer = new Importer();

    @Override
    public void execute(GameCharacter character, String command, String arguments) {
        // Steps:
        // 1. Verify specified path
        // 2. Verify database type (only glow at this time)
        // 3. Notify player that import is starting.
        // 4. Spawn thread for the importer.
        // 5. return

        // Spawned Thread will:
        // 1. execute the importer
        // 2. notify player when the import is complete.
        //importer.run(path);
    }
}
