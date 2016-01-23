package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WaitForNewPlayerSelectionTest {
    private static final String YES = RandomStringUtils.randomAlphabetic(3);
    private static final String SERVER_LOCALE = RandomStringUtils.randomAlphabetic(7);

    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final TextMaker textMaker = mock(TextMaker.class);
    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final Connection connection = new Connection(configuration);

    private final WaitForNewPlayerSelection stage = new WaitForNewPlayerSelection(injector, textMaker);

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(SERVER_LOCALE);
        when(textMaker.getText(TextName.Yes, SERVER_LOCALE)).thenReturn(YES);
    }

    @Test
    public void withoutInputReturnSameStage() {
        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, sameInstance(stage));
    }

    @Test
    public void withYesInputProgressToNextStage() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(7));
        connection.getInputQueue().push(YES);
        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PasswordPrompt.class));
    }

    @Test
    public void withYesInputRetainsLoginId() {
        String loginId = RandomStringUtils.randomAlphabetic(7);
        connection.getInputQueue().push(loginId);
        connection.getInputQueue().push(YES);
        stage.execute(connection, configuration);

        assertThat(connection.getInputQueue(), hasItem(loginId));
    }

    @Test
    public void withOtherInputPromptForLoginId() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(7));
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(4));

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(LoginIdPrompt.class));
    }
}
