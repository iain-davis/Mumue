package org.mumue.mumue;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mumue.mumue.configuration.commandline.CommandLineConfigurationModule;
import org.mumue.mumue.configuration.startup.StartupConfigurationModule;

public class Launcher {
    private Injector injector;

    public void launch(String... arguments) {
        injector = Guice.createInjector(
                new CommandLineConfigurationModule(arguments),
                new StartupConfigurationModule()
        );
        Mumue mumue = injector.getInstance(Mumue.class);
        mumue.run();
    }

    public Injector getInjector() {
        return injector;
    }
}
