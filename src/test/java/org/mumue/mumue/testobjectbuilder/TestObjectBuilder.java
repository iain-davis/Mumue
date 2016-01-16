package org.mumue.mumue.testobjectbuilder;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;

public class TestObjectBuilder {
    public static Connection connection() {
        return new Connection(configuration());
    }

    public static ApplicationConfiguration configuration() {
        return new MockApplicationConfiguration();
    }

    private static class MockApplicationConfiguration extends ApplicationConfiguration {
        public MockApplicationConfiguration() {
            super(null);
        }

        @Override
        public String getServerLocale() {
            return super.getServerLocale();
        }

        @Override
        public int getTelnetPort() {
            return super.getTelnetPort();
        }

        @Override
        public long getNewComponentId() {
            return super.getNewComponentId();
        }
    }
}
