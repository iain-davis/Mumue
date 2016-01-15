package org.mumue.mumue.connection.stages.login;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class LoginPromptTest {
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final Configuration configuration = mock(Configuration.class);
    private final LoginPrompt stage = new LoginPrompt(injector, textMaker);

    private final Connection connection = new Connection(configuration);
    private final String prompt = RandomStringUtils.randomAlphanumeric(17);

    @Before
    public void beforeEach() {
        when(textMaker.getText(Matchers.eq(TextName.LoginPrompt), anyString())).thenReturn(prompt);
    }

    @Test
    public void executeReturnsNextStage() {
        assertThat(stage.execute(connection, configuration), instanceOf(WaitForLoginId.class));
    }

    @Test
    public void executePutsLoginPromptOnOutputQueue() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(prompt));
    }
}
