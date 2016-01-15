package org.mumue.mumue;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.AfterClass;
import org.junit.Test;
import org.mumue.mumue.configuration.ConfigurationDefaults;

public class StartupAcceptanceTest {
    @AfterClass
    public static void tearDown() {
        new MumueRunner().cleanupDatabase();
    }

    @Test
    public void useDefaultTelnetPort() {
        MumueRunner runner = new MumueRunner();
        runner.run(ConfigurationDefaults.TELNET_PORT);

        assertThat(runner.getTelnetOutput(), containsString(MumueRunner.WELCOME_TO_MUMUE));
    }
}
