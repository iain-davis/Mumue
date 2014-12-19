package org.ruhlendavis.meta.configuration.commandline;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandLineProvider {
    private final String[] arguments;

    public CommandLineProvider(String... arguments) {
        this.arguments = arguments;
    }

    public CommandLine get() {
        Options options = new Options();
        options.addOption(getLongOption(CommandLineOptionName.TEST));
        options.addOption(getOptionWithArgument(CommandLineOptionName.STARTUP_CONFIGURATION_PATH));
        CommandLineParser parser = new BasicParser();
        try {
            return parser.parse(options, arguments);
        } catch (ParseException exception) {
            throw new RuntimeException(exception);
        }
    }

    private Option getOptionWithArgument(String name) {
        return new Option(name.substring(0, 1), name, true, "");
    }

    private Option getLongOption(String name) {
        return new Option(name, false, "");
    }
}
