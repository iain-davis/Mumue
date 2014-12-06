package org.ruhlendavis.meta;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.configuration.commandline.CommandLineProvider;
import org.ruhlendavis.meta.listener.Listener;

@RunWith(MockitoJUnitRunner.class)
public class MainTest {
    @Mock Listener listener;
    @InjectMocks Main main;

    @Test
    public void doNotRunForeverInTest() {
        main.run(listener, new CommandLineProvider("--test"));
    }

    @Test
    public void usePortFromConfiguration() {
        Integer port = RandomUtils.nextInt(1024, 65536);
        main.run(listener, new CommandLineProvider("--test", "--port", port.toString()));
        verify(listener).setPort(port.intValue());
    }
}
