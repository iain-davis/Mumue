package org.ruhlendavis.meta.connection.stages;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.text.TextMaker;
import org.ruhlendavis.meta.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class WelcomeStageTest {
    private final Collection<String> inputQueue = new ConcurrentLinkedQueue<>();
    private final Collection<String> outputQueue = new ConcurrentLinkedQueue<>();
    private final String welcome = RandomStringUtils.randomAlphanumeric(17);

    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @InjectMocks WelcomeStage stage;

    @Before
    public void beforeEach() {
        when(textMaker.getText(anyString(), eq(TextName.Welcome))).thenReturn(welcome);
    }

    @Test
    public void executeReturnsNextStage() {
        assertThat(stage.execute(inputQueue, outputQueue, configuration), instanceOf(ConnectionStage.class));
    }

    @Test
    public void executePutsWelcomeOnOutputQueue() {
        stage.execute(inputQueue, outputQueue, configuration);

        assertThat(outputQueue, contains(welcome));
    }
}
