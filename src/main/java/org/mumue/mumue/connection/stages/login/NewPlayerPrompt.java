package org.mumue.mumue.connection.stages.login;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Injector;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

import javax.inject.Inject;

public class NewPlayerPrompt implements ConnectionStage {
    private final Injector injector;
    private final TextMaker textMaker;

    @Inject
    public NewPlayerPrompt(Injector injector, TextMaker textMaker) {
        this.injector = injector;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        Map<String, String> variables = ImmutableMap.<String, String>builder()
                .put(TextMaker.TEXT_VARIABLE_LOGIN_ID, connection.getInputQueue().peek())
                .build();
        String text = textMaker.getText(TextName.NewPlayerPrompt, configuration.getServerLocale(), variables);
        connection.getOutputQueue().push(text);
        return injector.getInstance(WaitForNewPlayerSelection.class);
    }
}
