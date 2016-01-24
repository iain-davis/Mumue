package org.mumue.mumue.connection.states;

import javax.inject.Inject;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.player.PlayerDao;
import org.mumue.mumue.player.PlayerRepository;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class PlayerAuthentication implements ConnectionState {
    private final LoginIdPrompt loginIdPrompt;
    private final PlayerBuilder playerBuilder;
    private final PlayerConnected playerConnected;
    private final PlayerDao playerDao;
    private final PlayerRepository playerRepository;
    private final TextMaker textMaker;

    @Inject
    public PlayerAuthentication(LoginIdPrompt loginIdPrompt, PlayerBuilder playerBuilder, PlayerConnected playerConnected, PlayerDao playerDao, PlayerRepository playerRepository, TextMaker textMaker) {
        this.loginIdPrompt = loginIdPrompt;
        this.playerBuilder = playerBuilder;
        this.playerConnected = playerConnected;
        this.playerDao = playerDao;
        this.playerRepository = playerRepository;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String loginId = connection.getInputQueue().pop();
        String password = connection.getInputQueue().pop();
        Player player;

        if (playerDao.playerExistsFor(loginId)) {
            player = playerRepository.get(loginId, password);
            if (player.getId() == GlobalConstants.REFERENCE_UNKNOWN) {
                String text = textMaker.getText(TextName.LoginFailed, configuration.getServerLocale());
                connection.getOutputQueue().push(text);
                return loginIdPrompt;
            } else {
                String text = textMaker.getText(TextName.LoginSuccess, configuration.getServerLocale());
                connection.getOutputQueue().push(text);
            }
        } else {
            player = playerBuilder.withLoginId(loginId).build();
            playerRepository.add(player, password);
        }
        connection.setPlayer(player);
        return playerConnected;
    }
}
