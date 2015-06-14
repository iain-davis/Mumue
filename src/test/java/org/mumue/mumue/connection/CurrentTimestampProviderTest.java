package org.mumue.mumue.connection;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.Instant;

import org.junit.Test;

public class CurrentTimestampProviderTest {
    private final CurrentTimestampProvider provider = new CurrentTimestampProvider();

    @Test
    public void getNeverReturnsAnInstant() {
        assertNotNull(provider.get());

        assertThat(provider.get(), instanceOf(Instant.class));
    }
}
