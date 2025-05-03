package org.mumue.mumue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.mumue.mumue.importer.GlobalConstants;

public class MumueRunner {
    public static final String WELCOME_TO_MUMUE = "Welcome to Mumue!";

    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
    private Future<String> telnetOutput;
    private Future<?> mumueFuture;
    private ScheduledFuture<?> killerFuture;
    private PrintStream originalOut;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final Main main = new Main();

    public void run(int port, String... arguments) {
        redirectConsoleOutput();

        Callable<String> telnet = new TelnetCallable(port, outputStream);
        Runnable killer = () -> {
            main.stop();
            mumueFuture.cancel(true);
            telnetOutput.cancel(true);
        };

        mumueFuture = executorService.submit(() -> {
            try {
                main.run(arguments);
            } catch (Throwable thrown) {
                throw new RuntimeException(thrown);
            }
        });
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
        while (!mumueFuture.isDone()) ;
    }

    public synchronized void stopAfterTelnet() {
        //noinspection StatementWithEmptyBody,LoopConditionNotUpdatedInsideLoop
        while (!outputStream.toString().contains(GlobalConstants.TELNET_LISTENING)) ;
        stop();
    }

    private void redirectConsoleOutput() {
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    private void resetConsoleOutput() {
        System.setOut(originalOut);
    }

    public void cleanupDatabase() {
        FileUtils.getFile("./mumuedatabase.h2.db").deleteOnExit();
        FileUtils.getFile("./mumuedatabase.trace.db").deleteOnExit();
        FileUtils.getFile("./mumuedatabase.lock.db").deleteOnExit();
    }
}
