package org.ruhlendavis.mumue.interpreter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CommandNameMapProviderTest {
    private final CommandNameMapProvider provider = new CommandNameMapProvider();

    @Test
    public void getNeverReturnsNull() {
        assertNotNull(provider.get());
    }

    @Test
    public void getReturnsCorrectCount() {
        assertThat(provider.get().size(), equalTo(1));
    }
}
