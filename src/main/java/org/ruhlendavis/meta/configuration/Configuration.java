package org.ruhlendavis.meta.configuration;

import org.ruhlendavis.meta.configuration.commandline.CommandLineConfiguration;
import org.ruhlendavis.meta.configuration.online.OnlineConfiguration;
import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.text.TextDao;
import org.ruhlendavis.meta.text.TextName;

public class Configuration {
    private TextDao textDao;
    private CommandLineConfiguration commandLineConfiguration;
    private OnlineConfiguration onlineConfiguration;
    private StartupConfiguration startupConfiguration;

    public Configuration(CommandLineConfiguration commandLineConfiguration, OnlineConfiguration onlineConfiguration, StartupConfiguration startupConfiguration, TextDao textDao) {
        this.commandLineConfiguration = commandLineConfiguration;
        this.onlineConfiguration = onlineConfiguration;
        this.startupConfiguration = startupConfiguration;
        this.textDao = textDao;
    }

    public Configuration(CommandLineConfiguration commandLineConfiguration, StartupConfiguration startupConfiguration, OnlineConfiguration onlineConfiguration) {
        this.commandLineConfiguration = commandLineConfiguration;
        this.onlineConfiguration = onlineConfiguration;
        this.startupConfiguration = startupConfiguration;
    }

    public boolean isTest() {
        return commandLineConfiguration.isTest();
    }

    public String getServerLocale() {
        return onlineConfiguration.getServerLocale();
    }

    public int getTelnetPort() {
        return startupConfiguration.getTelnetPort();
    }

    public String getText(String serverLocale, TextName textName) {
        return textDao.getText(serverLocale, textName);
    }
}
