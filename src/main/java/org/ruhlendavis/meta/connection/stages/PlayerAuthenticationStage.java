package org.ruhlendavis.meta.connection.stages;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.TextQueue;
import org.ruhlendavis.meta.player.PlayerAuthenticationDao;
import org.ruhlendavis.meta.text.TextMaker;
import org.ruhlendavis.meta.text.TextName;

public class PlayerAuthenticationStage implements ConnectionStage {
    private PlayerAuthenticationDao dao = new PlayerAuthenticationDao();
    private TextMaker textMaker = new TextMaker();

    @Override
    public ConnectionStage execute(TextQueue inputQueue, TextQueue outputQueue, Configuration configuration) {
        String loginId = inputQueue.pop();
        String password = inputQueue.pop();
        if (dao.authenticate(loginId, password)) {
            return new NoOperationStage();
        }
        String text = textMaker.getText(configuration.getServerLocale(), TextName.LoginFailed);
        outputQueue.push(text);
        return new LoginPromptStage();
    }
}
