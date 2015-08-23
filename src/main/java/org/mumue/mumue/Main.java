package org.mumue.mumue;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mumue.mumue.configuration.commandline.CommandLineConfigurationModule;
import org.mumue.mumue.configuration.startup.StartupConfigurationModule;

public class Main {
    private Mumue mumue;

    public static void main(String... arguments) {
        new Main().run(arguments);
    }

    public void run(String... arguments) {
        Injector injector = Guice.createInjector(
                new CommandLineConfigurationModule(arguments),
                new StartupConfigurationModule()
        );
        mumue = injector.getInstance(Mumue.class);
        mumue.run();
    }

    public void stop() {
        mumue.stop();
    }
}
