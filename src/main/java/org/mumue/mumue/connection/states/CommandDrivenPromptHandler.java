package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerDao;
import org.mumue.mumue.player.PlayerRepository;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CommandDrivenPromptHandler implements ConnectionState {
    private final PlayerConnected playerConnected;
    private final CharacterDao characterDao;
    private final PlayerDao playerDao;
    private final PlayerRepository playerRepository;
    private final TextMaker textMaker;

    @Inject
    @Singleton
    public CommandDrivenPromptHandler(PlayerConnected playerConnected, CharacterDao characterDao, PlayerDao playerDao, PlayerRepository playerRepository, TextMaker textMaker) {
        this.playerConnected = playerConnected;
        this.characterDao = characterDao;
        this.playerDao = playerDao;
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
            if (playerDao.authenticate(player.getLoginId(), password)) {
                connection.setCharacter(character);
                connection.setPlayer(player);
                return playerConnected;
            } else {
                connection.getOutputQueue().push(textMaker.getText(TextName.LoginFailed, connection.getLocale()));
                return this;
            }
        }
    }
}
