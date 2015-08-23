package org.mumue.mumue.acceptance;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.*;

public class MumueRunner {
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
    private Future<String> telnetOutput;
    private Future<?> mumueFuture;
    private ScheduledFuture<?> killerFuture;
    private PrintStream originalOut;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private MumueRunnable mumue;

    public void runMumue(MumueRunnable mumue, int port) {
        this.mumue = mumue;
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        Callable<String> telnet = new TelnetCallable(port);

        mumueFuture = executorService.submit(mumue);
        telnetOutput = executorService.schedule(telnet, 5, TimeUnit.SECONDS);
        Runnable killer = () -> {
            mumueFuture.cancel(true);
            telnetOutput.cancel(true);
        };

        killerFuture = executorService.schedule(killer, 10, TimeUnit.SECONDS);
    }

    public String getTelnetOutput() {
        try {
            return telnetOutput.get();
        } catch (ExecutionException|InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getConsoleOutput() {
            return outputStream.toString();
    }

    public void stop() {
        mumue.stop();
        telnetOutput.cancel(true);
        mumueFuture.cancel(true);
        killerFuture.cancel(true);
        System.setOut(originalOut);
    }
}
