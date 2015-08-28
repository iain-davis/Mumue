package org.mumue.mumue.acceptance;

import org.apache.commons.net.telnet.TelnetClient;
import org.mumue.mumue.importer.GlobalConstants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Callable;

public class TelnetCallable implements Callable<String> {
    private final int port;
    private final ByteArrayOutputStream consoleOutputStream;

    public TelnetCallable(int port, ByteArrayOutputStream consoleOutputStream) {
        this.port = port;
        this.consoleOutputStream = consoleOutputStream;
    }

    @Override
    public String call() throws Exception {
        while (!consoleOutputStream.toString().contains(GlobalConstants.TELNET_LISTENING)) {}
        TelnetClient client = new TelnetClient();
        try {
            client.connect("localhost", port);
            InputStream input = client.getInputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;

            do {
                bytesRead = input.read(buffer);
                if (bytesRead > 0) {
                    return new String(buffer, 0, bytesRead);
                }
            }
            while (bytesRead >= 0);

            client.disconnect();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return "";
    }
}
