package org.ruhlendavis.meta.connection.stages;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;

@RunWith(MockitoJUnitRunner.class)
public class WaitForLoginIdStageTest {
    private final Collection<String> inputQueue = new ConcurrentLinkedQueue<>();
    private final Collection<String> outputQueue = new ConcurrentLinkedQueue<>();

    @Mock Configuration configuration;
    @InjectMocks WaitForLoginIdStage stage;

    @Test
    public void executeWithEmptyInputReturnsSameStage() {
        inputQueue.clear();

        ConnectionStage next = stage.execute(inputQueue, outputQueue, configuration);

        assertThat(next, instanceOf(WaitForLoginIdStage.class));
    }

    @Test
    public void executeWithOneInputReturnsPasswordPromptStage() {
        inputQueue.add(RandomStringUtils.randomAlphabetic(17));

        ConnectionStage next = stage.execute(inputQueue, outputQueue, configuration);

        assertThat(next, instanceOf(PasswordPromptStage.class));
    }
}
