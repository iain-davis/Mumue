package org.mumue.mumue;

import org.apache.commons.io.FileUtils;
import org.mumue.mumue.importer.GlobalConstants;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.*;

public class MumueRunner {
    public static final String WELCOME_TO_MUMUE = "Welcome to Mumue!";

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

    public void cleanupDatabase() {
        try {
            FileUtils.forceDelete(FileUtils.getFile("./mumuedatabase.h2.db"));
        } catch (FileNotFoundException ignored) {
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
