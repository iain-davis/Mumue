package org.mumue.mumue.databaseimporter;

import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.character.GameCharacter;

class GarbageImporter implements ComponentImporter {
    @Override
    public Component createComponent() {
        return new Garbage();
    }
}
