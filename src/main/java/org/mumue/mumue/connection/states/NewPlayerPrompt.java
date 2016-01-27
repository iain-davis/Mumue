package org.mumue.mumue.connection.states;

import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.common.collect.ImmutableMap;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
public class NewPlayerPrompt implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final TextMaker textMaker;

    @Inject
    public NewPlayerPrompt(ConnectionStateProvider connectionStateProvider, TextMaker textMaker) {
        this.connectionStateProvider = connectionStateProvider;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        Map<String, String> variables = ImmutableMap.<String, String>builder()
                .put(TextMaker.TEXT_VARIABLE_LOGIN_ID, connection.getInputQueue().peek())
                .build();
        String text = textMaker.getText(TextName.NewPlayerPrompt, configuration.getServerLocale(), variables);
        connection.getOutputQueue().push(text);
        return connectionStateProvider.get(NewPlayerHandler.class);
    }
}
