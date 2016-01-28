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

public class ImportPathPromptTest {
    private final Connection connection = Nimue.connection();
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final TextMaker textMaker = mock(TextMaker.class);
    private final ImportPathPrompt importPathPrompt = new ImportPathPrompt(connectionStateProvider, textMaker);

    @Test
    public void returnHandlerState() {
        when(textMaker.getText(TextName.ImportFilePathPrompt, ConfigurationDefaults.SERVER_LOCALE)).thenReturn("");

        ConnectionState next = importPathPrompt.execute(connection, configuration);

        assertThat(next, instanceOf(ImportPathPromptHandler.class));
    }

    @Test
    public void displayPrompt() {
        String text = RandomStringUtils.randomAlphabetic(25);
        when(textMaker.getText(TextName.ImportFilePathPrompt, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        importPathPrompt.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(text));
    }
}