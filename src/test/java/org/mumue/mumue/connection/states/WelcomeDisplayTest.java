package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.PortConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WelcomeDisplayTest {
    private final String welcome = RandomStringUtils.randomAlphanumeric(17);
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final Connection connection = new Connection(configuration);

    private final TextMaker textMaker = mock(TextMaker.class);
    private final StateCollection stateCollection = mock(StateCollection.class);

    private final WelcomeDisplay welcomeDisplay = new WelcomeDisplay(stateCollection, textMaker);

    @Before
    public void beforeEach() {
        String serverLocale = RandomStringUtils.randomAlphabetic(7);
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(eq(TextName.Welcome), eq(serverLocale))).thenReturn(welcome);
        when(stateCollection.get(StateName.LoginIdPrompt)).thenReturn(new LoginIdPrompt(stateCollection, textMaker));
        when(stateCollection.get(StateName.WelcomeCommandsDisplay)).thenReturn(new WelcomeCommandsPrompt(stateCollection, textMaker));
    }

    @Test
    public void executeReturnsLoginPromptStage() {
        PortConfiguration portConfiguration = new PortConfiguration();
        portConfiguration.setSupportsMenus(true);
        connection.setPortConfiguration(portConfiguration);
        assertThat(welcomeDisplay.execute(connection, configuration), instanceOf(LoginIdPrompt.class));
    }

    @Test
    public void executeReturnsConnectCommandPromptStage() {
        PortConfiguration portConfiguration = new PortConfiguration();
        portConfiguration.setSupportsMenus(false);
        connection.setPortConfiguration(portConfiguration);
        assertThat(welcomeDisplay.execute(connection, configuration), instanceOf(WelcomeCommandsPrompt.class));
    }


    @Test
    public void executePutsWelcomeOnOutputQueue() {
        welcomeDisplay.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(welcome));
    }
}
