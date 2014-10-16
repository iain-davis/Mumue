package org.ruhlendavis.meta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Properties;

public class ConfigurationPrompter {
    private InputStream input;
    private PrintStream output;

    public void run(InputStream inputStream, PrintStream output, Properties properties) {
        BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
        try {
            queryForPort(input, output, properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void queryForPort(BufferedReader input, PrintStream output, Properties properties) throws IOException {
        while (true) {
            output.println("Enter a port for the server:");
            String port = input.readLine();
            properties.setProperty("port", port);
            if (Integer.parseInt(properties.getProperty("port")) > 0) {
                break;
            }
            output.println("Invalid port.");
        }
    }
}
