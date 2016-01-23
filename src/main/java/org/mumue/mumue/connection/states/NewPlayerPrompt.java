package org.mumue.mumue.connection.states;

import java.util.Map;
import javax.inject.Inject;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Injector;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class NewPlayerPrompt implements ConnectionState {
    private final Injector injector;
    private final TextMaker textMaker;

    @Inject
    public NewPlayerPrompt(Injector injector, TextMaker textMaker) {
        this.injector = injector;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        Map<String, String> variables = ImmutableMap.<String, String>builder()
                .put(TextMaker.TEXT_VARIABLE_LOGIN_ID, connection.getInputQueue().peek())
                .build();
        String text = textMaker.getText(TextName.NewPlayerPrompt, configuration.getServerLocale(), variables);
        connection.getOutputQueue().push(text);
        return injector.getInstance(WaitForNewPlayerSelection.class);
    }
}
