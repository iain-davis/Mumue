package org.ruhlendavis.meta;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.ruhlendavis.meta.configuration.commandline.CommandLineFactory;
import org.ruhlendavis.meta.listener.Listener;

public class Main {
    public static void main(String... arguments) {
        Main main = new Main();
        main.run(arguments);
    }

    public void run(String... arguments) {
        Injector injector = Guice.createInjector();
        Meta meta = injector.getInstance(Meta.class);
        meta.run(System.out, new Listener(), arguments);
    }
}
