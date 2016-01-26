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
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class PasswordPromptTest {
    private final String prompt = RandomStringUtils.randomAlphanumeric(17);
    private final ConnectionStateService connectionStateService = TestObjectBuilder.stateService();
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Connection connection = new Connection(configuration);
    private final PasswordPrompt passwordPrompt = new PasswordPrompt(connectionStateService, textMaker);

    @Before
    public void beforeEach() {
        when(textMaker.getText(Matchers.eq(TextName.PasswordPrompt), anyString())).thenReturn(prompt);
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
