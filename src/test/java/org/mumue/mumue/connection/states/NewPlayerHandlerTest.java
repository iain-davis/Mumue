package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class NewPlayerHandlerTest {
    private static final String YES = RandomStringUtils.insecure().nextAlphabetic(3);
    private final TextMaker textMaker = mock(TextMaker.class);
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final Connection connection = new Connection(configuration);
    private ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final NewPlayerHandler newPlayerHandler = new NewPlayerHandler(connectionStateProvider, textMaker);

    @Before
    public void beforeEach() {
        when(textMaker.getText(TextName.Yes, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(YES);
    }

    @Test
    public void withoutInputReturnSameStage() {
        ConnectionState next = newPlayerHandler.execute(connection, configuration);

        assertThat(next, sameInstance(newPlayerHandler));
    }

    @Test
    public void withYesInputProgressToNextStage() {
        connection.getInputQueue().push(RandomStringUtils.insecure().nextAlphabetic(7));
        connection.getInputQueue().push(YES);
        ConnectionState next = newPlayerHandler.execute(connection, configuration);

        assertThat(next, instanceOf(PasswordPrompt.class));
    }

    @Test
    public void withYesInputRetainsLoginId() {
        String loginId = RandomStringUtils.insecure().nextAlphabetic(7);
        connection.getInputQueue().push(loginId);
        connection.getInputQueue().push(YES);
        newPlayerHandler.execute(connection, configuration);

        assertThat(connection.getInputQueue(), hasItem(loginId));
    }

    @Test
    public void withOtherInputPromptForLoginId() {
        connection.getInputQueue().push(RandomStringUtils.insecure().nextAlphabetic(7));
        connection.getInputQueue().push(RandomStringUtils.insecure().nextAlphabetic(4));

        ConnectionState next = newPlayerHandler.execute(connection, configuration);

        assertThat(next, instanceOf(LoginIdPrompt.class));
    }
}
