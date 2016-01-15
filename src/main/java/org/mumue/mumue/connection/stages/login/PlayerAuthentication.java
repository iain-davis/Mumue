package org.mumue.mumue.connection.stages.login;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.CurrentTimestampProvider;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.connection.stages.mainmenu.DisplayPlayerMenu;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.player.PlayerDao;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

import javax.inject.Inject;

public class PlayerAuthentication implements ConnectionStage {
    private final Injector injector;
    private final CurrentTimestampProvider currentTimestampProvider;
    private final PlayerBuilder playerBuilder;
    private final PlayerDao playerDao;
    private final TextMaker textMaker;

    @Inject
    public PlayerAuthentication(Injector injector, CurrentTimestampProvider currentTimestampProvider, PlayerBuilder playerBuilder, PlayerDao playerDao, TextMaker textMaker) {
        this.injector = injector;
        this.currentTimestampProvider = currentTimestampProvider;
        this.playerBuilder = playerBuilder;
        this.playerDao = playerDao;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        String loginId = connection.getInputQueue().pop();
        String password = connection.getInputQueue().pop();
        Player player = playerBuilder.build();

        if (playerDao.playerExistsFor(loginId)) {
            if (playerAuthenticates(loginId, password)) {
                player = loginSuccess(connection, configuration, loginId, password);
            } else {
                return loginFailure(connection, configuration);
            }
        } else {
            player.setLoginId(loginId);
            playerDao.createPlayer(player, password);
        }
        connection.setPlayer(player);
        return injector.getInstance(DisplayPlayerMenu.class);
    }

    private Player loginSuccess(Connection connection, Configuration configuration, String loginId, String password) {
        Player player = playerDao.getPlayer(loginId, password);
        player.setLastModified(currentTimestampProvider.get());
        player.countUse();
        String text = textMaker.getText(TextName.LoginSuccess, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return player;
    }

    private ConnectionStage loginFailure(Connection connection, Configuration configuration) {
        String text = textMaker.getText(TextName.LoginFailed, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return injector.getInstance(LoginPrompt.class);
    }

    private boolean playerAuthenticates(String loginId, String password) {
        return playerDao.authenticate(loginId, password);
    }
}
