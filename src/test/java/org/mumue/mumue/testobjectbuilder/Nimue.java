package org.mumue.mumue.testobjectbuilder;

import static org.mockito.Mockito.mock;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.mumue.mumue.components.character.CharacterBuilder;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.ComponentIdManager;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.states.ConnectionState;
import org.mumue.mumue.connection.states.ConnectionStateProvider;
import org.mumue.mumue.player.PlayerBuilder;

public class Nimue {
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

    public static ConnectionStateProvider stateProvider() {
        return new MockConnectionStateProvider();
    }

    public static ComponentIdManager componentIdManager() {
        return new MockComponentIdManager();
    }

    public static Instant nowInstant() {
        return Instant.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public static Instant randomInstant() {
        return nowInstant().minus(new Random().nextInt(1000) + 1, ChronoUnit.DAYS);
    }

    private static class MockConnectionStateProvider extends ConnectionStateProvider {
        public MockConnectionStateProvider() {
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
            super(null, null);
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

    private static class MockComponentIdManager extends ComponentIdManager {
        private static long lastComponentId = 1000;

        public MockComponentIdManager() {
            super(null);
        }

        @Override
        public synchronized long getNewComponentId() {
            lastComponentId++;
            return lastComponentId;
        }
    }
}
