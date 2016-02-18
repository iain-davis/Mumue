package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.universe.Universe;

class CharacterImporter extends ComponentImporter {
    public GameCharacter importFrom(List<String> lines, Universe universe) {
        GameCharacter character = new GameCharacter();
        character.setId(getId(lines));
        character.setName(getName(lines));
        character.setLocationId(getLocationId(lines));
        character.setUniverseId(universe.getId());
        return character;
    }
}
