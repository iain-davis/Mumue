package org.mumue.mumue.connection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Random;

import org.junit.Test;

public class ServerSocketFactoryTest {
    private final ServerSocketFactory builder = new ServerSocketFactory();
    @Test
    public void createSocketSetsThePort() {
        int port = new Random().nextInt(2000) + 1024;

        assertThat(builder.createSocket(port).getLocalPort(), equalTo(port));
    }
}
