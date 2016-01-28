package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class LoginIdPromptTest {
    private final TextMaker textMaker = mock(TextMaker.class);
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final LoginIdPrompt stage = new LoginIdPrompt(connectionStateProvider, textMaker);
    private final Connection connection = new Connection(configuration);
    private final String prompt = RandomStringUtils.randomAlphanumeric(17);

    @Before
    public void beforeEach() {
        when(textMaker.getText(Matchers.eq(TextName.LoginPrompt), anyString())).thenReturn(prompt);
    }

    @Test
    public void executeReturnsNextStage() {
        assertThat(stage.execute(connection, configuration), instanceOf(LoginIdHandler.class));
    }

    @Test
    public void executePutsLoginPromptOnOutputQueue() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(prompt));
    }
}
