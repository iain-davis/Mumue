package org.ruhlendavis.meta;

import org.apache.commons.cli.*;

public class MetaMain {
    public MetaMain(String[] arguments) {
        parseArguments(arguments);
    }

    private void parseArguments(String[] arguments) {
        Options options = new Options();
        Option convert = OptionBuilder.withArgName("import")
                .hasArgs(2)
                .withDescription("convert a GlowMUCK database")
                .create("convert");
        options.addOption(convert);

        CommandLineParser parser = new GnuParser();
        try {
            CommandLine line = parser.parse(options, arguments);
        }
        catch(ParseException exception) {
            System.err.println(exception.getMessage());
            System.err.println("Usage: ");
            System.err.println(" --import databaseType databasePath ");
        }
    }
}
