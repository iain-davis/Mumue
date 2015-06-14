package org.ruhlendavis.mumue.configuration.commandline;

import com.google.inject.Provider;
import org.apache.commons.cli.*;

class CommandLineProvider implements Provider<CommandLine> {
    private final String[] arguments;

    public CommandLineProvider(String... arguments) {
        this.arguments = arguments;
    }

    @Override
    public CommandLine get() {
        Options options = new Options();
        options.addOption(getLongOption(CommandLineOptionName.TEST));
        options.addOption(getOptionWithArgument(CommandLineOptionName.STARTUP_CONFIGURATION_PATH));
        CommandLineParser parser = new DefaultParser();
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
        return new Option(name.substring(0, 1), name, false, "");
    }
}
