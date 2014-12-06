package org.ruhlendavis.meta;

import org.ruhlendavis.meta.configuration.Configuration;
import org.ruhlendavis.meta.configuration.commandline.CommandLineProvider;
import org.ruhlendavis.meta.listener.Listener;

public class Main {
    private Configuration configuration;
    public static void main(String... arguments) {
        Main main = new Main();
        main.run(new Listener(), new CommandLineProvider(arguments));
    }

    public void run(Listener listener, CommandLineProvider commandLineProvider) {
        configuration = new Configuration(commandLineProvider.get());
        Thread thread = startListener(listener, configuration.getPort());
        while(listener.isRunning() && !configuration.isTest()) {}
        stopListener(listener, thread);
    }

    private Thread startListener(Listener listener, int port) {
        Thread thread = new Thread(listener);
        listener.setPort(port);
        thread.start();
        return thread;
    }

    private void stopListener(Listener listener, Thread thread) {
        listener.stop();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
