package org.mumue.mumue.testobjectbuilder;

import static org.mockito.Mockito.mock;

import java.util.Random;

import org.mumue.mumue.components.character.CharacterBuilder;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.states.ConnectionState;
import org.mumue.mumue.connection.states.ConnectionStateService;
import org.mumue.mumue.player.PlayerBuilder;

public class TestObjectBuilder {
    public static Connection connection() {
        return new Connection(configuration());
    }

    public static ApplicationConfiguration configuration() {
        return new MockApplicationConfiguration();
    }

    public static CharacterBuilder character() {
        return new CharacterBuilder();
    }

    public static PlayerBuilder player() {
        return new PlayerBuilder();
    }

    public static ConnectionStateService stateService() {
        return new MockConnectionStateService();
    }

    private static class MockConnectionStateService extends ConnectionStateService {
        public MockConnectionStateService() {
            super(null);
        }

        @Override
        public ConnectionState get(Class<? extends ConnectionState> stateClass) {
            return mock(stateClass);
        }
    }

    private static class MockApplicationConfiguration extends ApplicationConfiguration {
        private long lastComponentId = 100L * new Random().nextInt(37);

        public MockApplicationConfiguration() {
            super(null);
        }

        @Override
        public String getServerLocale() {
            return ConfigurationDefaults.SERVER_LOCALE;
        }

        @Override
        public long getNewComponentId() {
            lastComponentId++;
            return lastComponentId;
        }
    }
}
