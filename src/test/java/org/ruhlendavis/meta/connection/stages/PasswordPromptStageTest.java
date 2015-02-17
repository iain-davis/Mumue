package org.ruhlendavis.meta.connection.stages;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.connection.TextQueue;
import org.ruhlendavis.meta.text.TextMaker;
import org.ruhlendavis.meta.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class PasswordPromptStageTest {
    private final TextQueue inputQueue = new TextQueue();
    private final TextQueue outputQueue = new TextQueue();
    private final String prompt = RandomStringUtils.randomAlphanumeric(17);

    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @InjectMocks PasswordPromptStage stage;

    @Before
    public void beforeEach() {
        when(textMaker.getText(anyString(), eq(TextName.PasswordPrompt))).thenReturn(prompt);
    }

    @Test
    public void executeReturnsNextStage() {
        assertThat(stage.execute(inputQueue, outputQueue, configuration), instanceOf(WaitForPasswordStage.class));
    }

    @Test
    public void executePutsLoginPromptOnOutputQueue() {
        stage.execute(inputQueue, outputQueue, configuration);

        assertThat(outputQueue, contains(prompt));
    }
}
