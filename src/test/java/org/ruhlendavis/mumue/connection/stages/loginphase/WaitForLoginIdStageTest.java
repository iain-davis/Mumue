package org.ruhlendavis.mumue.connection.stages.loginphase;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;

@RunWith(MockitoJUnitRunner.class)
public class WaitForLoginIdStageTest {
    private final Connection connection = new Connection();

    @Mock Configuration configuration;
    @InjectMocks WaitForLoginIdStage stage;

    @Test
    public void executeWithEmptyInputReturnsSameStage() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(WaitForLoginIdStage.class));
    }

    @Test
    public void executeWithOneInputReturnsPasswordPromptStage() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(17));

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PasswordPromptStage.class));
    }
}
