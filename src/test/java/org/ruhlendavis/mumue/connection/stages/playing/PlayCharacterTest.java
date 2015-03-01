package org.ruhlendavis.mumue.connection.stages.playing;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;

@RunWith(MockitoJUnitRunner.class)
public class PlayCharacterTest {
    private final Connection connection = new Connection();

    @Mock Configuration configuration;
    @InjectMocks PlayCharacter stage;

    @Test
    public void returnPlayCharacterStage() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayCharacter.class));
    }
}
