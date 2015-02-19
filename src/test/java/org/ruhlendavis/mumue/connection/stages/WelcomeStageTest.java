package org.ruhlendavis.mumue.connection.stages;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class WelcomeStageTest {
    private final Connection connection = new Connection();
    private final String welcome = RandomStringUtils.randomAlphanumeric(17);

    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @InjectMocks WelcomeStage stage;

    @Before
    public void beforeEach() {
        when(textMaker.getText(eq(TextName.Welcome), anyString())).thenReturn(welcome);
    }

    @Test
    public void executeReturnsLoginPromptStage() {
        assertThat(stage.execute(connection, configuration), instanceOf(LoginPromptStage.class));
    }

    @Test
    public void executePutsWelcomeOnOutputQueue() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(welcome));
    }
}
