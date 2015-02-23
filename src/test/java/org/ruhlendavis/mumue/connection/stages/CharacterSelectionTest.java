package org.ruhlendavis.mumue.connection.stages;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
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
public class CharacterSelectionTest {
    private final String prompt = RandomStringUtils.randomAlphanumeric(17);
    private final String locale = RandomStringUtils.randomAlphabetic(15);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(5);
    private final Player player = new Player().withLocale(locale);
    private final Connection connection = new Connection().withPlayer(player);

    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @InjectMocks CharacterSelection stage;

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(TextName.CharacterSelectionPrompt, locale, serverLocale)).thenReturn(prompt);
    }

    @Test
    public void returnWaitForCharacterSelection() {
        ConnectionStage next = stage.execute(connection, configuration);
        assertNotNull(next);
        assertThat(next, instanceOf(WaitForCharacterSelection.class));
    }

    @Test
    public void putPromptOnOutputQueue() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(prompt));
    }
}
