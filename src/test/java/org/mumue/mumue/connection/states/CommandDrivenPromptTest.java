package org.mumue.mumue.connection.states;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CommandDrivenPromptTest {
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Connection connection = new Connection(configuration);
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final CommandDrivenPrompt commandDrivenPrompt = new CommandDrivenPrompt(connectionStateProvider, textMaker);

    @Test
    public void returnWaitForWelcomeScreenCommand() {
        when(textMaker.getText(TextName.WelcomeCommands, ConfigurationDefaults.SERVER_LOCALE)).thenReturn("");

        ConnectionState returned = commandDrivenPrompt.execute(connection, configuration);

        assertThat(returned, instanceOf(CommandDrivenHandler.class));
    }

    @Test
    public void displayCommands() {
        String text = RandomStringUtils.insecure().nextAlphabetic(13);
        when(textMaker.getText(TextName.WelcomeCommands, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        commandDrivenPrompt.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(text));
    }
}
