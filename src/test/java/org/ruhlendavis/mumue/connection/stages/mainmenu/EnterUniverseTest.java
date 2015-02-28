package org.ruhlendavis.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.components.GameCharacter;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class EnterUniverseTest {
    private final String message = RandomStringUtils.randomAlphabetic(25);
    private final String locale = RandomStringUtils.randomAlphabetic(16);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(15);
    private final Player player = new Player().withLocale(locale);

    private final Connection connection = new Connection().withPlayer(player);//.withCharacter(character);

    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @InjectMocks EnterUniverse stage;

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(TextName.EnterUniverse, locale, serverLocale)).thenReturn(message);
    }

    @Test
    public void executeReturnsEnterSpace() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(EnterSpace.class));
    }

    @Test
    public void enterUniverseDisplayEntryMessage() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(message));
    }
}
