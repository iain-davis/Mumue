package org.ruhlendavis.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Collection;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.components.GameCharacter;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.connection.stages.NoOperationStage;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.text.TextMaker;

@RunWith(MockitoJUnitRunner.class)
public class CreateCharacterTest {
    private final String locale = RandomStringUtils.randomAlphabetic(16);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(15);
    private final Player player = new Player().withLocale(locale);
    private final Connection connection = new Connection().withPlayer(player);

    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @InjectMocks CreateCharacter stage;

    @Test
    public void neverReturnNull() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(17));
        ConnectionStage next = stage.execute(connection, configuration);

        assertNotNull(next);
    }

    @Test
    public void nextStageOnValidCharacter() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(17));

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(NoOperationStage.class));
    }

    @Test
    public void createCharacter() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(17));

        stage.execute(connection, configuration);

        assertThat(player.getCharacters().size(), equalTo(1));
    }

    @Test
    public void createdCharacterShouldBeNamed() {
        String name = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(name);

        stage.execute(connection, configuration);

        Collection<GameCharacter> characters = player.getCharacters();

        assertThat(characters.size(), equalTo(1));
        assertThat(characters.stream().findFirst().get().getName(), equalTo(name));
    }
}
