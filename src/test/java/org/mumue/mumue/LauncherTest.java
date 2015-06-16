package org.mumue.mumue;

import com.google.inject.Provider;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.configuration.startup.StartupConfiguration;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class LauncherTest {
    private final Launcher launcher = new Launcher();

    @Test
    public void injectorHasCommandLineProvider() {
        launcher.launch("--test");
        // This will throw an error if we don't have a provider
        launcher.getInjector().getProvider(CommandLine.class);
    }

    @Test
    public void injectorHasCommandLineProviderWithArguments() {
        String[] arguments = {RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(8), "--test"};
        String[] expected = {arguments[0], arguments[1]};
        launcher.launch(arguments);
        Provider<CommandLine> provider = launcher.getInjector().getProvider(CommandLine.class);

        assertThat(provider.get().getArgs(), equalTo(expected));
    }

    @Test
    public void injectorHasStartupConfigurationProvider() {
        launcher.launch("--test");
        // This will throw an error if we don't have a provider
        launcher.getInjector().getProvider(StartupConfiguration.class);
    }
}