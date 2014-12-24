package org.ruhlendavis.meta;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.ruhlendavis.meta.configuration.commandline.CommandLineFactory;
import org.ruhlendavis.meta.injection.MetaModule;
import org.ruhlendavis.meta.listener.Listener;

public class Main {
    public static void main(String... arguments) {
        Injector injector = Guice.createInjector(new MetaModule());
        Meta meta = injector.getInstance(Meta.class);
        meta.run(System.out, new Listener(), new CommandLineFactory(arguments));
    }
}
