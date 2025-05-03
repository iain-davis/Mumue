package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

import java.util.Locale;

public class LoginIdPromptTest {
    private final TextMaker textMaker = mock(TextMaker.class);
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final LoginIdPrompt stage = new LoginIdPrompt(connectionStateProvider, textMaker);
    private final Connection connection = new Connection(configuration);
    private final String prompt = RandomStringUtils.insecure().nextAlphanumeric(17);

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(Locale.ENGLISH.toString());
        when(textMaker.getText(eq(TextName.LoginPrompt), anyString())).thenReturn(prompt);
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
