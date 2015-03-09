package org.ruhlendavis.mumue.connection.stages.login;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;

public class WaitForPasswordTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock Configuration configuration;
    @InjectMocks WaitForPassword stage;

    private final Connection connection = new Connection(configuration);

    @Test
    public void executeWithEmptyInputReturnsSameStage() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(WaitForPassword.class));
    }

    @Test
    public void executeWithTwoInputReturnsAuthenticationStage() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(17));
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(17));

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerAuthentication.class));
    }
}
