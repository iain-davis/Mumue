package org.mumue.mumue.connection.stages.login;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.text.TextName;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;

public class WaitForNewPlayerSelectionTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock TextMaker textMaker;
    @Mock Configuration configuration;
    @InjectMocks WaitForNewPlayerSelection stage;

    private final Connection connection = new Connection(configuration);
    private final String yes = RandomStringUtils.randomAlphabetic(3);
    private String serverLocale = RandomStringUtils.randomAlphabetic(7);

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(TextName.Yes, serverLocale)).thenReturn(yes);
    }

    @Test
    public void withoutInputReturnSameStage() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, sameInstance(stage));
    }

    @Test
    public void withYesInputProgressToNextStage() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(7));
        connection.getInputQueue().push(yes);
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PasswordPrompt.class));
    }

    @Test
    public void withYesInputRetainsLoginId() {
        String loginId = RandomStringUtils.randomAlphabetic(7);
        connection.getInputQueue().push(loginId);
        connection.getInputQueue().push(yes);
        stage.execute(connection, configuration);

        assertThat(connection.getInputQueue(), hasItem(loginId));
    }

    @Test
    public void withOtherInputPromptForLoginId() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(7));
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(4));

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(LoginPrompt.class));
    }
}
