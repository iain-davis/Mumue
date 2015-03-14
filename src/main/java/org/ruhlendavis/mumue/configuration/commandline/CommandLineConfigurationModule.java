package org.ruhlendavis.mumue.configuration.commandline;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import org.apache.commons.cli.CommandLine;

import org.ruhlendavis.mumue.configuration.commandline.CommandLineProvider;

public class CommandLineConfigurationModule extends AbstractModule {
    private final String[] arguments;

    public CommandLineConfigurationModule(String... arguments) {
        this.arguments = arguments;
    }

    @Override
    protected void configure() {
        bind(CommandLine.class).toProvider(new CommandLineProvider(arguments)).in(Singleton.class);
    }
}
