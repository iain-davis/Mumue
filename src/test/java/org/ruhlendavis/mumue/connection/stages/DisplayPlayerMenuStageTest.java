package org.ruhlendavis.mumue.connection.stages;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
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
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class DisplayPlayerMenuStageTest {
    private final String menu = RandomStringUtils.randomAlphanumeric(17);
    private final String locale = RandomStringUtils.randomAlphabetic(15);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(5);
    private final Player player = new Player().withLocale(locale);
    private final Connection connection = new Connection().withPlayer(player);

    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @InjectMocks DisplayPlayerMenuStage stage;

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(eq(TextName.PlayerMainMenu), eq(locale), eq(serverLocale))).thenReturn(menu);
    }

    @Test
    public void executeReturnsNextStage() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(ConnectionStage.class));
    }

    @Test
    public void executePutsMainMenuOnOutputQueue() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(menu));
    }
}
