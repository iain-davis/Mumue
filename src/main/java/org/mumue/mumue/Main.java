package org.mumue.mumue;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mumue.mumue.configuration.commandline.CommandLineConfigurationModule;
import org.mumue.mumue.configuration.startup.StartupConfigurationModule;

public class Main {
    public static void main(String... arguments) {
        Injector injector = Guice.createInjector(
                new CommandLineConfigurationModule(arguments),
                new StartupConfigurationModule()
        );
        Mumue mumue = injector.getInstance(Mumue.class);
        mumue.run();
    }
}
