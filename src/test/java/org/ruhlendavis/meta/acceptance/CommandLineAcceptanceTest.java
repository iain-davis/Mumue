package org.ruhlendavis.meta.acceptance;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.Main;
import org.ruhlendavis.meta.configuration.commandline.CommandLineProvider;
import org.ruhlendavis.meta.constants.Defaults;
import org.ruhlendavis.meta.listener.Listener;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineAcceptanceTest {
    private final Listener listener = new Listener();
    private final Main main = new Main();

    @Test
    public void doNotRunForeverInTestMode() {
        main.run(listener, new CommandLineProvider("--test"));
    }

    @Test
    public void listenOnDefaultPort() {
        main.run(listener, new CommandLineProvider("--test"));
        assertThat(listener.getServerSocket().getLocalPort(), equalTo(Defaults.TELNET_PORT));
    }

    @Test
    public void listenOnPortSpecifiedOnCommandLine() {
        String port = RandomStringUtils.randomNumeric(4);
        main.run(listener, new CommandLineProvider("--port", port, "--test"));
        assertThat(listener.getServerSocket().getLocalPort(), equalTo(Integer.valueOf(port)));
    }
}
