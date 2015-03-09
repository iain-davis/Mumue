package org.ruhlendavis.mumue.connection.stages.login;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.CurrentTimestampProvider;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.connection.stages.mainmenu.DisplayPlayerMenu;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.player.PlayerBuilder;
import org.ruhlendavis.mumue.player.PlayerDao;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

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
