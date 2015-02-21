package org.ruhlendavis.mumue.connection.stages;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
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
public class PlayerMainMenuChoiceStageTest {
    String locale = RandomStringUtils.randomAlphabetic(16);
    String serverLocale = RandomStringUtils.randomAlphabetic(15);
    private final Connection connection = new Connection().withPlayer(new Player().withLocale(locale));

    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @InjectMocks PlayerMainMenuChoiceStage stage;

    @Test
    public void neverReturnNull() {
        assertNotNull(stage.execute(connection, configuration));
    }

    @Test
    public void continueWaitOnNoInput() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, sameInstance(stage));
    }

    @Test
    public void goToCreateCharacterOnCInput() {
        connection.getInputQueue().push("C");

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CreateCharacterStage.class));
    }

    @Test
    public void acceptLowerCase() {
        connection.getInputQueue().push("c");

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(CreateCharacterStage.class));
    }

    @Test
    public void goToPlayerCharacterOnPInput() {
        connection.getInputQueue().push("P");

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayCharacterStage.class));
    }

    @Test
    public void redisplayMenuOnInvalidInput() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(3));
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(TextName.InvalidOption, locale, serverLocale)).thenReturn("");

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(DisplayPlayerMenuStage.class));

    }

    @Test
    public void displayInvalidOptionMessageOnInvalidInput() {
        String message = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(3));
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(TextName.InvalidOption, locale, serverLocale)).thenReturn(message);

        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), contains(message));

    }
}
