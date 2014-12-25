package org.ruhlendavis.meta;

import org.ruhlendavis.meta.listener.Listener;

public class Main {
    public static void main(String... arguments) {
        Main main = new Main();
        main.run(arguments);
    }

    public void run(String... arguments) {
        Meta meta = new Meta();
        meta.run(new Listener(), arguments);
    }
}
