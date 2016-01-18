package org.mumue.mumue;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mumue.mumue.configuration.ConfigurationDefaults;

public class StartupAcceptanceTest {
    private static final MumueRunner mumueRunner = new MumueRunner();

    @AfterClass
    public static void tearDown() {
        mumueRunner.cleanupDatabase();
    }

    @Test
    public void useDefaultTelnetPort() {
        MumueRunner runner = mumueRunner;
        runner.run(ConfigurationDefaults.TELNET_PORT);

        assertThat(runner.getTelnetOutput(), containsString(MumueRunner.WELCOME_TO_MUMUE));
    }
}
