package org.mumue.mumue.acceptance;

import org.mumue.mumue.Main;
import org.mumue.mumue.importer.GlobalConstants;

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
    private Main main = new Main();

    public void run(int port, String... arguments) {
        redirectConsoleOutput();

        Callable<String> telnet = new TelnetCallable(port, outputStream);
        Runnable killer = () -> {
            main.stop();
            mumueFuture.cancel(true);
            telnetOutput.cancel(true);
        };

        mumueFuture = executorService.submit(() -> main.run(arguments));
        telnetOutput = executorService.submit(telnet);
        killerFuture = executorService.schedule(killer, 10, TimeUnit.SECONDS);
    }

    public String getTelnetOutput() {
        try {
            return telnetOutput.get();
        } catch (ExecutionException | InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getConsoleOutput() {
        return outputStream.toString();
    }

    public synchronized void stop() {
        main.stop();
        telnetOutput.cancel(true);
        mumueFuture.cancel(true);
        killerFuture.cancel(true);
        resetConsoleOutput();
        //noinspection StatementWithEmptyBody
        while(!mumueFuture.isDone());
    }

    public synchronized void stopAfterTelnet() {
        //noinspection StatementWithEmptyBody
        while (!outputStream.toString().contains(GlobalConstants.TELNET_LISTENING));
        stop();
    }

    private void redirectConsoleOutput() {
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    private void resetConsoleOutput() {
        System.setOut(originalOut);
    }
}
