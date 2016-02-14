package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.character.GameCharacter;

class CharacterImporter extends ComponentImporter {
    public GameCharacter importFrom(List<String> lines) {
        GameCharacter character = new GameCharacter();
        character.setId(getId(lines));
        character.setName(getName(lines));
        character.setLocationId(getLocationId(lines));
        return character;
    }
}
