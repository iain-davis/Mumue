package org.mumue.mumue.connection.stages.login;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;
import org.mumue.mumue.player.PlayerDao;

public class WaitForLoginIdTest {
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final Configuration configuration = mock(Configuration.class);
    private final PlayerDao dao = mock(PlayerDao.class);
    private final Connection connection = new Connection(configuration);
    private final WaitForLoginId stage = new WaitForLoginId(injector, dao);

    @Test
    public void executeWithEmptyInputReturnsSameStage() {
        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(WaitForLoginId.class));
    }

    @Test
    public void executeWithValidLoginIdPromptsForPassword() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(loginId);
        when(dao.playerExistsFor(loginId)).thenReturn(true);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(PasswordPrompt.class));
    }

    @Test
    public void executeWithInvalidLoginIdPromptsForNewPlayer() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(loginId);
        when(dao.playerExistsFor(loginId)).thenReturn(false);

        ConnectionStage next = stage.execute(connection, configuration);

        assertThat(next, instanceOf(NewPlayerPrompt.class));
    }

    @Test
    public void executeWithValidIdLeavesLoginIdOnQueue() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(loginId);
        when(dao.playerExistsFor(loginId)).thenReturn(true);

        stage.execute(connection, configuration);

        assertThat(connection.getInputQueue(), hasItem(loginId));
    }

    @Test
    public void executeWithInValidIdLeavesLoginIdOnQueue() {
        String loginId = RandomStringUtils.randomAlphabetic(17);
        connection.getInputQueue().push(loginId);
        when(dao.playerExistsFor(loginId)).thenReturn(false);

        stage.execute(connection, configuration);

        assertThat(connection.getInputQueue(), hasItem(loginId));
    }
}
