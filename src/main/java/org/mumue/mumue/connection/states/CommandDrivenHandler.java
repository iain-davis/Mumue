package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerRepository;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
public class CommandDrivenHandler implements ConnectionState {
    private final ConnectionStateService connectionStateService;
    private final CharacterDao characterDao;
    private final PlayerRepository playerRepository;
    private final TextMaker textMaker;

    @Inject
    public CommandDrivenHandler(ConnectionStateService connectionStateService, CharacterDao characterDao, PlayerRepository playerRepository, TextMaker textMaker) {
        this.connectionStateService = connectionStateService;
        this.characterDao = characterDao;
        this.playerRepository = playerRepository;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().hasAny()) {
            String command = connection.getInputQueue().pop();
            String[] words = command.split(" ");
            if (words.length == 2) {
                connection.getOutputQueue().push(textMaker.getText(TextName.MissingPassword, connection.getLocale()));
                return this;
            }
            if (words.length == 1) {
                connection.getOutputQueue().push(textMaker.getText(TextName.MissingCharacterName, connection.getLocale()));
                return this;
            }
            if (isConnectCommand(words[0])) {
                return handleConnect(connection, words[1], words[2]);
            } else {
                connection.getOutputQueue().push(textMaker.getText(TextName.WelcomeCommands, connection.getLocale()));
                return this;
            }
        }
        return this;
    }

    private boolean isConnectCommand(String word) {
        return word.toLowerCase().startsWith("con");
    }

    private ConnectionState handleConnect(Connection connection, String characterName, String password) {
        GameCharacter character = characterDao.getCharacter(characterName);
        if (character.getId() == GlobalConstants.REFERENCE_UNKNOWN) {
            connection.getOutputQueue().push(textMaker.getText(TextName.CharacterDoesNotExist, connection.getLocale()));
            return this;
        } else {
            Player player = playerRepository.get(character.getPlayerId());
            if (authenticate(player, password)) {
                connection.setCharacter(character);
                connection.setPlayer(player);
                return connectionStateService.get(PlayerConnected.class);
            } else {
                connection.getOutputQueue().push(textMaker.getText(TextName.LoginFailed, connection.getLocale()));
                return this;
            }
        }
    }

    private boolean authenticate(Player player, String password) {
        return playerRepository.get(player.getLoginId(), password).getId() != GlobalConstants.REFERENCE_UNKNOWN;
    }
}
