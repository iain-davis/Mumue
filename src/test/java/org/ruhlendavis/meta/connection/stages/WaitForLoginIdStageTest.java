package org.ruhlendavis.meta.connection.stages;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.TextQueue;

@RunWith(MockitoJUnitRunner.class)
public class WaitForLoginIdStageTest {
    private final TextQueue inputQueue = new TextQueue();
    private final TextQueue outputQueue = new TextQueue();

    @Mock Configuration configuration;
    @InjectMocks WaitForLoginIdStage stage;

    @Test
    public void executeWithEmptyInputReturnsSameStage() {
        ConnectionStage next = stage.execute(inputQueue, outputQueue, configuration);

        assertThat(next, instanceOf(WaitForLoginIdStage.class));
    }

    @Test
    public void executeWithOneInputReturnsPasswordPromptStage() {
        inputQueue.push(RandomStringUtils.randomAlphabetic(17));

        ConnectionStage next = stage.execute(inputQueue, outputQueue, configuration);

        assertThat(next, instanceOf(PasswordPromptStage.class));
    }
}
