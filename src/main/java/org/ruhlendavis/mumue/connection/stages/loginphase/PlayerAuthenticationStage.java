package org.ruhlendavis.mumue.connection.stages.loginphase;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.CurrentTimestampProvider;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.connection.stages.mainmenu.DisplayPlayerMenu;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.player.PlayerDao;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class PlayerAuthenticationStage implements ConnectionStage {
    private PlayerDao dao = new PlayerDao();
    private TextMaker textMaker = new TextMaker();
    private CurrentTimestampProvider currentTimestampProvider = new CurrentTimestampProvider();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        String loginId = connection.getInputQueue().pop();
        String password = connection.getInputQueue().pop();

        if (dao.authenticate(loginId, password)) {
            String text = textMaker.getText(TextName.LoginSuccess, configuration.getServerLocale());
            connection.getOutputQueue().push(text);
            Player player = dao.getPlayer(loginId, password);
            player.setLastModified(currentTimestampProvider.get());
            player.countUse();
            connection.setPlayer(player);
            return new DisplayPlayerMenu();
        }

        String text = textMaker.getText(TextName.LoginFailed, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return new LoginPromptStage();
    }
}
