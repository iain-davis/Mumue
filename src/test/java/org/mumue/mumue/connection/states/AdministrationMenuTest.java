package org.mumue.mumue.connection.states;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class AdministrationMenuTest {
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final TextMaker textMaker = mock(TextMaker.class);
    private final AdministrationMenu administrationMenu = new AdministrationMenu(connectionStateProvider, textMaker);
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final Connection connection = Nimue.connection();

    @Test
    public void returnMenuHandlerState() {
        when(textMaker.getText(TextName.AdministrationMenu, ConfigurationDefaults.SERVER_LOCALE)).thenReturn("");

        ConnectionState returned = administrationMenu.execute(connection, configuration);

        assertThat(returned, instanceOf(AdministrationMenuHandler.class));
    }

    @Test
    public void displayAdministrationMenu() {
        String menu = RandomStringUtils.randomAlphabetic(13);
        when(textMaker.getText(TextName.AdministrationMenu, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(menu);

        administrationMenu.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(menu));
    }
}