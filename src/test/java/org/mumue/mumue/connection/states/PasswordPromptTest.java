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

public class PasswordPromptTest {
    private final String prompt = RandomStringUtils.randomAlphanumeric(17);
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Connection connection = new Connection(configuration);
    private final PasswordPrompt passwordPrompt = new PasswordPrompt(connectionStateProvider, textMaker);

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(Locale.ENGLISH.toString());
        when(textMaker.getText(eq(TextName.PasswordPrompt), anyString())).thenReturn(prompt);
    }

    @Test
    public void executeReturnsNextStage() {
        assertThat(passwordPrompt.execute(connection, configuration), instanceOf(PasswordHandler.class));
    }

    @Test
    public void executePutsLoginPromptOnOutputQueue() {
        passwordPrompt.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(prompt));
    }
}
