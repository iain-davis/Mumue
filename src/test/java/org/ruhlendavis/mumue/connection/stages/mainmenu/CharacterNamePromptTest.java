package org.ruhlendavis.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.player.PlayerBuilder;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class CharacterNamePromptTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @InjectMocks CharacterNamePrompt stage;

    private final String prompt = RandomStringUtils.randomAlphanumeric(17);
    private final String locale = RandomStringUtils.randomAlphabetic(15);
    private final Player player = new PlayerBuilder().withLocale(locale).build();
    private final Connection connection = new Connection(configuration).withPlayer(player);

    @Before
    public void beforeEach() {
        when(textMaker.getText(eq(TextName.CharacterNamePrompt), eq(locale))).thenReturn(prompt);
    }

    @Test
    public void neverReturnNull() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertNotNull(next);
    }

    @Test
    public void nextStageIsWaitForPlayerName() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(WaitForCharacterName.class));
    }

    @Test
    public void promptForPlayerName() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(prompt));
    }
}
