package org.mumue.mumue.connection.stages.login;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;
import org.mumue.mumue.text.TextName;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;

public class NewPlayerPromptTest {
    private static final String NEW_PLAYER_NAME = RandomStringUtils.randomAlphabetic(13);
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final Configuration configuration = mock(Configuration.class);
    private final NewPlayerPrompt stage = new NewPlayerPrompt(injector, textMaker);

    private final Connection connection = new Connection(configuration);
    private final String prompt = RandomStringUtils.randomAlphanumeric(17);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(16);

    @Before
    public void beforeEach() {
        connection.getInputQueue().push(NEW_PLAYER_NAME);
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(Matchers.eq(TextName.NewPlayerPrompt), eq(serverLocale), anyMap())).thenReturn(prompt);
    }

    @Test
    public void returnWaitForNewPlayerSelectionStage() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(WaitForNewPlayerSelection.class));
    }

    @Test
    public void displayPromptForNewPlayer() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(prompt));
    }

}
