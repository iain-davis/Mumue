package org.mumue.mumue.acceptance;

import java.util.concurrent.*;

public class MumueRunner {
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
    private Future<String> telnetOutput;

    public void runMumue(Runnable mumue, int port) {
        Callable<String> telnet = new TelnetCallable(port);

        Future<?> mumueFuture = executorService.submit(mumue);
        telnetOutput = executorService.schedule(telnet, 4, TimeUnit.SECONDS);
        Runnable killer = () -> {
            mumueFuture.cancel(true);
            telnetOutput.cancel(true);
        };

        executorService.schedule(killer, 10, TimeUnit.SECONDS);
    }

    public String getOutput() {
        try {
            return telnetOutput.get();
        } catch (ExecutionException|InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }
}
