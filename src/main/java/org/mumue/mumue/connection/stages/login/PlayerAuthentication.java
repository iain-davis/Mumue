package org.mumue.mumue.connection.stages.login;

import org.mumue.mumue.connection.CurrentTimestampProvider;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.text.TextName;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.connection.stages.mainmenu.DisplayPlayerMenu;
import org.mumue.mumue.player.PlayerBuilder;
import org.mumue.mumue.player.PlayerDao;
import org.mumue.mumue.text.TextMaker;

public class PlayerAuthentication implements ConnectionStage {
    private PlayerBuilder playerBuilder = new PlayerBuilder();
    private PlayerDao dao = new PlayerDao();
    private TextMaker textMaker = new TextMaker();
    private CurrentTimestampProvider currentTimestampProvider = new CurrentTimestampProvider();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        String loginId = connection.getInputQueue().pop();
        String password = connection.getInputQueue().pop();
        Player player = playerBuilder.build();

        if (dao.playerExistsFor(loginId)) {
            if (playerAuthenticates(loginId, password)) {
                player = loginSuccess(connection, configuration, loginId, password);
            } else {
                return loginFailure(connection, configuration);
            }
        } else {
            player.setLoginId(loginId);
            dao.createPlayer(player, password);
        }
        connection.setPlayer(player);
        return new DisplayPlayerMenu();
    }

    private Player loginSuccess(Connection connection, Configuration configuration, String loginId, String password) {
        Player player = dao.getPlayer(loginId, password);
        player.setLastModified(currentTimestampProvider.get());
        player.countUse();
        String text = textMaker.getText(TextName.LoginSuccess, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return player;
    }

    private ConnectionStage loginFailure(Connection connection, Configuration configuration) {
        String text = textMaker.getText(TextName.LoginFailed, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return new LoginPrompt();
    }

    private boolean playerAuthenticates(String loginId, String password) {
        return dao.authenticate(loginId, password);
    }
}
