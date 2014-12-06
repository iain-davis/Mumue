package org.ruhlendavis.meta.configuration.file;

public class FileConfigurationAnalyzer {
    public boolean isValid(FileConfiguration fileConfiguration) {
        return !(fileConfiguration.getTelnetPort() < 1 || fileConfiguration.getTelnetPort() > 65535);
    }
}
