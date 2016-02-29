package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.character.GameCharacter;

class GameCharacterImporter implements ComponentImporter<GameCharacter> {
    private static final int HOME_LOCATION_ID_INDEX = 0;
    private static final int PASSWORD_INDEX = 3;

    @Override
    public GameCharacter importFrom(List<String> lines) {
        ImportCharacter character = new ImportCharacter();
        int baseIndex = lines.size() - FuzzballDatabaseItemType.CHARACTER.getCodaSize();
        character.setHomeLocationId(Long.parseLong(lines.get(baseIndex + HOME_LOCATION_ID_INDEX)));
        character.setPassword(lines.get(baseIndex + PASSWORD_INDEX));
        return character;
    }
}
