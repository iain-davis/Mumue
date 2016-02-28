package org.mumue.mumue.databaseimporter;

import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.MufProgram;

class MufProgramImporter implements ComponentImporter {
    @Override
    public Component createComponent() {
        return new MufProgram();
    }
}
