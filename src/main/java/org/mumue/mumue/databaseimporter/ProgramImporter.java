package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.Program;

class ProgramImporter extends ComponentImporter {
    public Program importFrom(List<String> lines) {
        Program program = new Program();
        program.setId(getId(lines));
        program.setName(getName(lines));
        program.setLocationId(getLocationId(lines));
        return program;
    }
}
