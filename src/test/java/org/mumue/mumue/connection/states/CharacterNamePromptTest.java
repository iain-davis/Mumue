package org.mumue.mumue.connection.states;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CharacterNamePromptTest {
    private final String prompt = RandomStringUtils.insecure().nextAlphanumeric(17);
    private final Player player = Nimue.player().build();
    private final ApplicationConfiguration configuration = Nimue.configuration();
    private final TextMaker textMaker = mock(TextMaker.class);
    private final Connection connection = new Connection(configuration).withPlayer(player);
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final CharacterNamePrompt characterNamePrompt = new CharacterNamePrompt(connectionStateProvider, textMaker);

    @Test
    void nextStageIsWaitForPlayerName() {
        when(textMaker.getText(TextName.CharacterNamePrompt, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(prompt);
        ConnectionState next = characterNamePrompt.execute(connection, configuration);

        assertThat(next, instanceOf(CharacterNameHandler.class));
    }

    @Test
    void promptForPlayerName() {
        when(textMaker.getText(TextName.CharacterNamePrompt, ConfigurationDefaults.SERVER_LOCALE)).thenReturn(prompt);
        characterNamePrompt.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(prompt));
    }
}
