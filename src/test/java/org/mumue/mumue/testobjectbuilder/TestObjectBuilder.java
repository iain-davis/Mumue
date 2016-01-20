package org.mumue.mumue.testobjectbuilder;

import org.mumue.mumue.components.character.CharacterBuilder;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;

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

    private static class MockApplicationConfiguration extends ApplicationConfiguration {
        public MockApplicationConfiguration() {
            super(null);
        }
    }
}
