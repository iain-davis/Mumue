package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class NewPlayerPromptTest {
    private static final String NEW_PLAYER_NAME = RandomStringUtils.randomAlphabetic(13);
    private final TextMaker textMaker = mock(TextMaker.class);
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final String prompt = RandomStringUtils.randomAlphanumeric(17);
    private final Connection connection = new Connection(configuration);
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final NewPlayerPrompt newPlayerPrompt = new NewPlayerPrompt(connectionStateProvider, textMaker);

    @Before
    public void beforeEach() {
        connection.getInputQueue().push(NEW_PLAYER_NAME);
        when(textMaker.getText(eq(TextName.NewPlayerPrompt), eq(ConfigurationDefaults.SERVER_LOCALE), anyMapOf(String.class, String.class))).thenReturn(prompt);
    }

    @Test
    public void returnWaitForNewPlayerSelectionStage() {
        ConnectionState next = newPlayerPrompt.execute(connection, configuration);

        assertThat(next, instanceOf(NewPlayerHandler.class));
    }

    @Test
    public void displayPromptForNewPlayer() {
        newPlayerPrompt.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(prompt));
    }
}
