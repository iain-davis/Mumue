package org.mumue.mumue.interpreter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Properties;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;
import org.mumue.mumue.database.DatabaseConfiguration;
import org.mumue.mumue.database.DatabaseModule;

public class CommandNameMapProviderTest {
    private final Injector injector = Guice.createInjector(new DatabaseModule(new DatabaseConfiguration(new Properties())));
    private final CommandNameMapProvider provider = new CommandNameMapProvider(injector);

    @Test
    public void getNeverReturnsNull() {
        assertNotNull(provider.get());
    }

    @Test
    public void getReturnsCorrectCount() {
        assertThat(provider.get().size(), equalTo(3));
    }
}
