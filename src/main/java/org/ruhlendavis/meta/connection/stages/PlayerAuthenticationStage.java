package org.ruhlendavis.meta.connection.stages;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.Connection;
import org.ruhlendavis.meta.player.PlayerDao;
import org.ruhlendavis.meta.text.TextMaker;
import org.ruhlendavis.meta.text.TextName;

public class PlayerAuthenticationStage implements ConnectionStage {
    private PlayerDao dao = new PlayerDao();
    private TextMaker textMaker = new TextMaker();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        String loginId = connection.getInputQueue().pop();
        String password = connection.getInputQueue().pop();
        if (dao.authenticate(loginId, password)) {
            String text = textMaker.getText(configuration.getServerLocale(), TextName.LoginSuccess);
            connection.getOutputQueue().push(text);
            return new NoOperationStage();
        }
        String text = textMaker.getText(configuration.getServerLocale(), TextName.LoginFailed);
        connection.getOutputQueue().push(text);
        return new LoginPromptStage();
    }
}
