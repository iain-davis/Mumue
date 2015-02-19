package org.ruhlendavis.mumue.connection.stages;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.player.PlayerDao;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class PlayerAuthenticationStage implements ConnectionStage {
    private PlayerDao dao = new PlayerDao();
    private TextMaker textMaker = new TextMaker();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        String loginId = connection.getInputQueue().pop();
        String password = connection.getInputQueue().pop();

        if (dao.authenticate(loginId, password)) {
            String text = textMaker.getText(TextName.LoginSuccess, configuration.getServerLocale());
            connection.getOutputQueue().push(text);
            connection.setPlayer(dao.getPlayer(loginId, password));
            return new NoOperationStage();
        }

        String text = textMaker.getText(TextName.LoginFailed, configuration.getServerLocale());
        connection.getOutputQueue().push(text);
        return new LoginPromptStage();
    }
}
