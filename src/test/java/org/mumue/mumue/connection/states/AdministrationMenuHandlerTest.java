package org.mumue.mumue.connection.states;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

class AdministrationMenuHandlerTest {
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final TextMaker textMaker = mock(TextMaker.class);
    private final AdministrationMenuHandler administrationMenuHandler = new AdministrationMenuHandler(connectionStateProvider, textMaker);

    @Test
    void rePromptOnInvalidOption() {
        String text = RandomStringUtils.insecure().nextAlphabetic(13);
        Connection connection = Nimue.connection();
        connection.getInputQueue().push("@");

        when(textMaker.getText(TextName.InvalidOption, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(text);

        ConnectionState returned = administrationMenuHandler.execute(connection, configuration);

        assertThat(returned, instanceOf(AdministrationMenu.class));
        assertThat(connection.getOutputQueue(), hasItem(text));
    }

    @Test
    void waitForInput() {
        ConnectionState returned = administrationMenuHandler.execute(Nimue.connection(), configuration);

        assertThat(returned, instanceOf(AdministrationMenuHandler.class));
    }

    @Test
    void nextStateForImport() {
        Connection connection = Nimue.connection();
        connection.getInputQueue().push("I");

        ConnectionState returned = administrationMenuHandler.execute(connection, configuration);

        assertThat(returned, instanceOf(ImportPathPrompt.class));
    }

    @Test
    void returnToPlayerMenu() {
        Connection connection = Nimue.connection();
        connection.getInputQueue().push("E");

        ConnectionState returned = administrationMenuHandler.execute(connection, configuration);

        assertThat(returned, instanceOf(PlayerMenuPrompt.class));
    }

    @Test
    void nextStateForImportLowerCase() {
        Connection connection = Nimue.connection();
        connection.getInputQueue().push("i");

        ConnectionState returned = administrationMenuHandler.execute(connection, configuration);

        assertThat(returned, instanceOf(ImportPathPrompt.class));
    }
}
