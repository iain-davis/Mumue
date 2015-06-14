package org.mumue.mumue.connection.stages.login;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextName;
import org.mumue.mumue.text.TextMaker;

public class PasswordPromptTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @InjectMocks PasswordPrompt stage;

    private final Connection connection = new Connection(configuration);
    private final String prompt = RandomStringUtils.randomAlphanumeric(17);

    @Before
    public void beforeEach() {
        when(textMaker.getText(Matchers.eq(TextName.PasswordPrompt), anyString())).thenReturn(prompt);
    }

    @Test
    public void executeReturnsNextStage() {
        assertThat(stage.execute(connection, configuration), instanceOf(WaitForPassword.class));
    }

    @Test
    public void executePutsLoginPromptOnOutputQueue() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(prompt));
    }
}
