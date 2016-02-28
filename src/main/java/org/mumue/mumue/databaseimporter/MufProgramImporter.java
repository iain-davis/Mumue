package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.MufProgram;

class MufProgramImporter implements ComponentImporter<MufProgram> {

    @Override
    public MufProgram importFrom(List<String> lines) {
        MufProgram character = new MufProgram();
        return character;
    }
}
