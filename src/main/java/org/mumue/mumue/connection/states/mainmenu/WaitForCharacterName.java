package org.mumue.mumue.connection.states.mainmenu;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.universe.UniverseDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.states.ConnectionState;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WaitForCharacterName implements ConnectionState {
    private final Injector injector;
    private final CharacterDao characterDao;
    private final TextMaker textMaker;
    private final UniverseDao universeDao;

    @Inject
    public WaitForCharacterName(Injector injector, CharacterDao characterDao, TextMaker textMaker, UniverseDao universeDao) {
        this.injector = injector;
        this.characterDao = characterDao;
        this.textMaker = textMaker;
        this.universeDao = universeDao;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }

        String name = connection.getInputQueue().pop();
        GameCharacter character = connection.getCharacter();
        long universeId = character.getUniverseId();

        if (nameTakenInUniverse(name, universeId)) {
            String text = textMaker.getText(TextName.CharacterNameAlreadyExists, connection.getLocale());
            connection.getOutputQueue().push(text);
            return injector.getInstance(CharacterNamePrompt.class);
        }

        if (nameTakenByOtherPlayer(name, connection.getPlayer().getId())) {
            String text = textMaker.getText(TextName.CharacterNameTakenByOtherPlayer, connection.getLocale());
            connection.getOutputQueue().push(text);
            return injector.getInstance(CharacterNamePrompt.class);
        }

        character.setName(name);
        character.setId(configuration.getNewComponentId());
        character.setPlayerId(connection.getPlayer().getId());
        character.setLocationId(universeDao.getUniverse(universeId).getStartingSpaceId());
        character.setHomeLocationId(character.getLocationId());
        characterDao.createCharacter(character);
        return injector.getInstance(DisplayPlayerMenu.class);
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
