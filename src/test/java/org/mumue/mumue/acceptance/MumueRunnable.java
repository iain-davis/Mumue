package org.mumue.mumue.acceptance;

import org.mumue.mumue.Main;

public class MumueRunnable implements Runnable {
    private final String[] arguments;
    private final Main main = new Main();

    public MumueRunnable(String... arguments) {
        this.arguments = arguments;
    }

    @Override
    public void run() {
        main.run(arguments);
    }

    public void stop() {
        main.stop();
    }
}
