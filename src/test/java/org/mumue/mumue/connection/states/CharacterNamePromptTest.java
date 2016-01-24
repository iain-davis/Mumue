package org.mumue.mumue.connection.states;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.testobjectbuilder.TestObjectBuilder;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class CharacterNamePromptTest {
    private final String prompt = RandomStringUtils.randomAlphanumeric(17);
    private final Player player = TestObjectBuilder.player().build();
    private final ApplicationConfiguration configuration = TestObjectBuilder.configuration();
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Connection connection = new Connection(configuration).withPlayer(player);
    private final CharacterNamePrompt characterNamePrompt = new CharacterNamePrompt(mock(WaitForCharacterName.class), textMaker);

    @Test
    public void nextStageIsWaitForPlayerName() {
        when(textMaker.getText(TextName.CharacterNamePrompt, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(prompt);
        ConnectionState next = characterNamePrompt.execute(connection, configuration);

        assertThat(next, instanceOf(WaitForCharacterName.class));
    }

    @Test
    public void promptForPlayerName() {
        when(textMaker.getText(TextName.CharacterNamePrompt, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(prompt);
        characterNamePrompt.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(prompt));
    }
}
