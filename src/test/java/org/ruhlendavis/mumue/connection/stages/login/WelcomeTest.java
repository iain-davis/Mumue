package org.ruhlendavis.mumue.connection.stages.login;

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
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class WelcomeTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @InjectMocks Welcome stage;

    private final Connection connection = new Connection(configuration);
    private final String welcome = RandomStringUtils.randomAlphanumeric(17);

    @Before
    public void beforeEach() {
        when(textMaker.getText(eq(TextName.Welcome), anyString())).thenReturn(welcome);
    }

    @Test
    public void executeReturnsLoginPromptStage() {
        assertThat(stage.execute(connection, configuration), instanceOf(LoginPrompt.class));
    }

    @Test
    public void executePutsWelcomeOnOutputQueue() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(welcome));
    }
}
