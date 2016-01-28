package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.player.PlayerRepository;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
class PlayerAuthentication implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final PlayerBuilder playerBuilder;
    private final PlayerRepository playerRepository;
    private final TextMaker textMaker;

    @Inject
    public PlayerAuthentication(ConnectionStateProvider connectionStateProvider, PlayerBuilder playerBuilder, PlayerRepository playerRepository, TextMaker textMaker) {
        this.connectionStateProvider = connectionStateProvider;
        this.playerBuilder = playerBuilder;
        this.playerRepository = playerRepository;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String loginId = connection.getInputQueue().pop();
        String password = connection.getInputQueue().pop();
        Player player;

        if (isValid(loginId)) {
            player = playerRepository.get(loginId, password);
            if (player.getId() == GlobalConstants.REFERENCE_UNKNOWN) {
                String text = textMaker.getText(TextName.LoginFailed, configuration.getServerLocale());
                connection.getOutputQueue().push(text);
                return connectionStateProvider.get(LoginIdPrompt.class);
            } else {
                String text = textMaker.getText(TextName.LoginSuccess, configuration.getServerLocale());
                connection.getOutputQueue().push(text);
            }
        } else {
            player = playerBuilder.withLoginId(loginId).build();
            playerRepository.add(player, password);
        }
        connection.setPlayer(player);
        return connectionStateProvider.get(PlayerConnected.class);
    }

    public boolean isValid(String loginId) {
        return playerRepository.get(loginId).getId() != GlobalConstants.REFERENCE_UNKNOWN;
    }
}
