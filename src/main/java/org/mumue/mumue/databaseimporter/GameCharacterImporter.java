package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.character.GameCharacter;

class GameCharacterImporter implements ComponentImporter<GameCharacter> {
    private static final int HOME_LOCATION_ID_INDEX = 3;

    @Override
    public GameCharacter importFrom(List<String> lines) {
        GameCharacter character = new GameCharacter();
        int baseIndex = lines.size() - FuzzballDatabaseItemType.CHARACTER.getCodaSize();
        character.setHomeLocationId(Long.parseLong(lines.get(baseIndex + HOME_LOCATION_ID_INDEX)));
        return character;
    }

}
