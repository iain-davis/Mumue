package org.mumue.mumue.connection.states.login;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.states.ConnectionState;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class WelcomeCommandsDisplayTest {
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));

    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Connection connection = new Connection(configuration);
    private final WelcomeCommandsDisplay stage = new WelcomeCommandsDisplay(injector, textMaker);

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(ConfigurationDefaults.SERVER_LOCALE);
    }

    @Test
    public void returnWaitForWelcomeScreenCommand() {
        when(textMaker.getText(TextName.WelcomeCommands, ConfigurationDefaults.SERVER_LOCALE)).thenReturn("");

        ConnectionState returned = stage.execute(connection, configuration);
        assertThat(returned, instanceOf(WaitForWelcomeScreenCommand.class));
    }

    @Test
    public void displayCommands() {
        String text = RandomStringUtils.randomAlphabetic(13);

        when(textMaker.getText(TextName.WelcomeCommands, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(text));
    }
}