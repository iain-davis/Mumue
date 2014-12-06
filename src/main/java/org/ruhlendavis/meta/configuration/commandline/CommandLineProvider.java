package org.ruhlendavis.meta.configuration.commandline;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandLineProvider {
    private final String[] arguments;

    public CommandLineProvider(String... arguments) {
        this.arguments = arguments;
    }

    public CommandLine get() {
        Options options = new Options();
        options.addOption("test", false, "");
        options.addOption("p", "port", true, "");
        CommandLineParser parser = new BasicParser();
        try {
            return parser.parse(options, arguments);
        } catch (ParseException exception) {
            throw new RuntimeException(exception);
        }
    }
}
