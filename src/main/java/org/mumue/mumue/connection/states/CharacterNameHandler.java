package org.mumue.mumue.connection.states;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.universe.UniverseRepository;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
class CharacterNameHandler implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final CharacterDao characterDao;
    private final TextMaker textMaker;
    private final UniverseRepository universeRepository;

    @Inject
    public CharacterNameHandler(ConnectionStateProvider connectionStateProvider, CharacterDao characterDao, TextMaker textMaker, UniverseRepository universeRepository) {
        this.connectionStateProvider = connectionStateProvider;
        this.characterDao = characterDao;
        this.textMaker = textMaker;
        this.universeRepository = universeRepository;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }

        String name = connection.getInputQueue().pop();
        if (StringUtils.isEmpty(name)) {
            return connectionStateProvider.get(PlayerMenuPrompt.class);
        }

        GameCharacter character = connection.getCharacter();
        long universeId = character.getUniverseId();

        if (nameTakenInUniverse(name, universeId)) {
            String text = textMaker.getText(TextName.CharacterNameAlreadyExists, connection.getLocale());
            connection.getOutputQueue().push(text);
            return connectionStateProvider.get(CharacterNamePrompt.class);
        }

        if (nameTakenByOtherPlayer(name, connection.getPlayer().getId())) {
            String text = textMaker.getText(TextName.CharacterNameTakenByOtherPlayer, connection.getLocale());
            connection.getOutputQueue().push(text);
            return connectionStateProvider.get(CharacterNamePrompt.class);
        }

        character.setName(name);
        character.setId(configuration.getNewComponentId());
        character.setPlayerId(connection.getPlayer().getId());
        character.setLocationId(universeRepository.getUniverse(universeId).getStartingSpaceId());
        character.setHomeLocationId(character.getLocationId());
        characterDao.createCharacter(character);
        return connectionStateProvider.get(PlayerMenuPrompt.class);
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
