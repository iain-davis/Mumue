package org.ruhlendavis.meta.connection.stages;

import java.util.Collection;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.player.PlayerAuthenticationDao;
import org.ruhlendavis.meta.text.TextMaker;
import org.ruhlendavis.meta.text.TextName;

public class PlayerAuthenticationStage implements ConnectionStage {
    private PlayerAuthenticationDao dao = new PlayerAuthenticationDao();
    private TextMaker textMaker = new TextMaker();

    @Override
    public ConnectionStage execute(Collection<String> inputQueue, Collection<String> outputQueue, Configuration configuration) {
        String loginId = inputQueue.stream().findFirst().get();
        inputQueue.remove(loginId);
        String password = inputQueue.stream().findFirst().get();
        inputQueue.remove(password);
        if (dao.authenticate(loginId, password)) {
            return new NoOperationStage();
        }
        String message = textMaker.getText(configuration.getServerLocale(), TextName.LoginFailed);
        outputQueue.add(message);
        return new LoginPromptStage();
    }
}
