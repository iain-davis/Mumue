package org.ruhlendavis.mumue.connection.stages.mainmenu;

import org.ruhlendavis.mumue.components.character.CharacterDao;
import org.ruhlendavis.mumue.components.character.GameCharacter;
import org.ruhlendavis.mumue.components.universe.UniverseDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.importer.GlobalConstants;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class WaitForCharacterName implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();
    private CharacterDao characterDao = new CharacterDao();
    private UniverseDao universeDao = new UniverseDao();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }

        String name = connection.getInputQueue().pop();
        GameCharacter character = connection.getCharacter();
        long universeId = character.getUniverseId();

        if (nameTakenInUniverse(name, universeId)) {
            String text = textMaker.getText(TextName.CharacterNameAlreadyExists, connection.getLocale());
            connection.getOutputQueue().push(text);
            return new CharacterNamePrompt();
        }

        if (nameTakenByOtherPlayer(name, connection.getPlayer().getId())) {
            String text = textMaker.getText(TextName.CharacterNameTakenByOtherPlayer, connection.getLocale());
            connection.getOutputQueue().push(text);
            return new CharacterNamePrompt();
        }

        character.setName(name);
        character.setId(configuration.getNewComponentId());
        character.setPlayerId(connection.getPlayer().getId());
        character.setLocationId(universeDao.getUniverse(universeId).getStartingSpaceId());
        character.setHomeLocationId(character.getLocationId());
        characterDao.createCharacter(character);
        return new DisplayPlayerMenu();
    }

    private boolean nameTakenByOtherPlayer(String name, long playerId) {
        GameCharacter character = characterDao.getCharacter(name);
        return character.getId() != GlobalConstants.REFERENCE_UNKNOWN && character.getPlayerId() != playerId;
    }

    private boolean nameTakenInUniverse(String name, long universeId) {
        GameCharacter character = characterDao.getCharacter(name, universeId);
        return character.getId() != GlobalConstants.REFERENCE_UNKNOWN;
    }
}
