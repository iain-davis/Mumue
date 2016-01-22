package org.mumue.mumue.connection.states.login;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.states.ConnectionState;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;

public class WaitForPasswordTest {
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final WaitForPassword stage = new WaitForPassword(injector);

    private final ApplicationConfiguration configuration = mock(ApplicationConfiguration.class);
    private final Connection connection = new Connection(configuration);

    @Test
    public void executeWithEmptyInputReturnsSameStage() {
        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(WaitForPassword.class));
    }

    @Test
    public void executeWithTwoInputReturnsAuthenticationStage() {
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(17));
        connection.getInputQueue().push(RandomStringUtils.randomAlphabetic(17));

        ConnectionState next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PlayerAuthentication.class));
    }
}
