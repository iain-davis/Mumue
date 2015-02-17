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
import org.ruhlendavis.meta.player.PlayerAuthenticationDao;
import org.ruhlendavis.meta.text.TextMaker;
import org.ruhlendavis.meta.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class PlayerAuthenticationStageTest {
    private final String loginId = RandomStringUtils.randomAlphanumeric(13);
    private final String password = RandomStringUtils.randomAlphanumeric(17);
    private final String message = RandomStringUtils.randomAlphanumeric(16);

    private final Collection<String> inputQueue = new ConcurrentLinkedQueue<>();
    private final Collection<String> outputQueue = new ConcurrentLinkedQueue<>();

    @Mock Configuration configuration;
    @Mock PlayerAuthenticationDao dao;
    @Mock TextMaker textMaker;
    @InjectMocks PlayerAuthenticationStage stage;

    @Before
    public void beforeEach() {
        inputQueue.add(loginId);
        inputQueue.add(password);
        when(textMaker.getText(anyString(), eq(TextName.LoginFailed))).thenReturn(message);
    }

    @Test
    public void executeWithValidCredentialsReturnsNextStage() {
        when(dao.authenticate(loginId, password)).thenReturn(true);

        ConnectionStage next = stage.execute(inputQueue, outputQueue, configuration);

        assertThat(next, instanceOf(NoOperationStage.class));
    }

    @Test
    public void executeWithInvalidCredentialsReturnsLoginPromptStage() {
        when(dao.authenticate(loginId, password)).thenReturn(false);

        ConnectionStage next = stage.execute(inputQueue, outputQueue, configuration);

        assertThat(next, instanceOf(LoginPromptStage.class));
    }

    @Test
    public void executeWithInvalidCredentialsPutsLoginFailedMessageOnOutputQueue() {
        stage.execute(inputQueue, outputQueue, configuration);

        assertThat(outputQueue, contains(message));
    }
}
