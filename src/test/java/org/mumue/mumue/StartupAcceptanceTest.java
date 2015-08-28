package org.mumue.mumue;

import org.junit.Ignore;
import org.junit.Test;
import org.mumue.mumue.configuration.ConfigurationDefaults;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class StartupAcceptanceTest {
    @Ignore
    @Test
    public void useDefaultTelnetPort() {
        MumueRunner runner = new MumueRunner();
        runner.run(ConfigurationDefaults.TELNET_PORT);

        assertThat(runner.getTelnetOutput(), containsString(MumueRunner.WELCOME_TO_MUMUE));
        runner.cleanupDatabase();
    }
}
