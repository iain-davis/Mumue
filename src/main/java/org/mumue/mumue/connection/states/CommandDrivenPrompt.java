package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CommandDrivenPrompt implements ConnectionState {
    private final CommandDrivenPromptHandler commandDrivenPromptHandler;
    private final TextMaker textMaker;

    @Inject
    @Singleton
    public CommandDrivenPrompt(CommandDrivenPromptHandler commandDrivenPromptHandler, TextMaker textMaker) {
        this.commandDrivenPromptHandler = commandDrivenPromptHandler;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        String text = textMaker.getText(TextName.WelcomeCommands, connection.getLocale());
        connection.getOutputQueue().push(text);
        return commandDrivenPromptHandler;
    }
}
