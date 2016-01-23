package org.mumue.mumue.connection.states;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WelcomeCommandsPromptTest {
    private final StateCollection stateCollection = mock(StateCollection.class);
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Connection connection = new Connection(configuration);

    private final WelcomeCommandsPrompt welcomeCommandsPrompt = new WelcomeCommandsPrompt(stateCollection, textMaker);

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(ConfigurationDefaults.SERVER_LOCALE);
        when(stateCollection.get(StateName.WelcomeCommandsHandler)).thenReturn(new WelcomeCommandsHandler(null,null,null, null));
    }

    @Test
    public void returnWaitForWelcomeScreenCommand() {
        when(textMaker.getText(TextName.WelcomeCommands, ConfigurationDefaults.SERVER_LOCALE)).thenReturn("");

        ConnectionState returned = welcomeCommandsPrompt.execute(connection, configuration);
        assertThat(returned, instanceOf(WelcomeCommandsHandler.class));
    }

    @Test
    public void displayCommands() {
        String text = RandomStringUtils.randomAlphabetic(13);

        when(textMaker.getText(TextName.WelcomeCommands, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        welcomeCommandsPrompt.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(text));
    }
}