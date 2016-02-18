package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.Program;
import org.mumue.mumue.components.universe.Universe;

class ProgramImporter extends ComponentImporter {
    public Program importFrom(List<String> lines, Universe universe) {
        Program program = new Program();
        program.setId(getId(lines));
        program.setName(getName(lines));
        program.setLocationId(getLocationId(lines));
        program.setUniverseId(universe.getId());
        return program;
    }
}
