package org.mumue.mumue.connection.stages.login;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.components.character.CharacterDao;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.connection.stages.playing.EnterUniverse;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerDao;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WaitForWelcomeScreenCommand implements ConnectionStage {
    private final Injector injector;
    private final CharacterDao characterDao;
    private final PlayerDao playerDao;
    private final TextMaker textMaker;

    @Inject
    public WaitForWelcomeScreenCommand(Injector injector, CharacterDao characterDao, PlayerDao playerDao, TextMaker textMaker) {
        this.injector = injector;
        this.characterDao = characterDao;
        this.playerDao = playerDao;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionStage execute(Connection connection, ApplicationConfiguration configuration) {
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

    private ConnectionStage handleConnect(Connection connection, String characterName, String password) {
        GameCharacter character = characterDao.getCharacter(characterName);
        if (character.getId() == GlobalConstants.REFERENCE_UNKNOWN) {
            connection.getOutputQueue().push(textMaker.getText(TextName.CharacterDoesNotExist, connection.getLocale()));
            return this;
        } else {
            Player player = playerDao.getPlayer(character.getPlayerId());
            if (playerDao.authenticate(player.getLoginId(), password)) {
                connection.setCharacter(character);
                connection.setPlayer(player);
                return injector.getInstance(EnterUniverse.class);
            } else {
                connection.getOutputQueue().push(textMaker.getText(TextName.LoginFailed, connection.getLocale()));
                return this;
            }
        }
    }
}
