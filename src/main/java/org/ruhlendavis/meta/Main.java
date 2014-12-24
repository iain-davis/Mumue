package org.ruhlendavis.meta;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.ruhlendavis.meta.configuration.commandline.CommandLineProvider;
import org.ruhlendavis.meta.listener.Listener;

public class Main {
    public static void main(String... arguments) {
        Injector injector = Guice.createInjector();
        Meta meta = injector.getInstance(Meta.class);
        meta.run(System.out, new Listener(), new CommandLineProvider(arguments));
    }
}
