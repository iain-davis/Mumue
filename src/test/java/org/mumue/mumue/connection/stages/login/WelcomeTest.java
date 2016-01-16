package org.mumue.mumue.connection.stages.login;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
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

public class WelcomeTest {
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Welcome stage = new Welcome(injector, textMaker);

    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final Connection connection = new Connection(configuration);
    private final String welcome = RandomStringUtils.randomAlphanumeric(17);

    @Before
    public void beforeEach() {
        String serverLocale = RandomStringUtils.randomAlphabetic(7);
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(eq(TextName.Welcome), eq(serverLocale))).thenReturn(welcome);
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
