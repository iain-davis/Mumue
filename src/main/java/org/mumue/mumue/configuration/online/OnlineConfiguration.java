package org.mumue.mumue.configuration.online;

import org.apache.commons.lang3.StringUtils;
import org.mumue.mumue.configuration.ConfigurationDefaults;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class OnlineConfiguration {
    private final OnlineConfigurationDao dao;

    @Inject
    public OnlineConfiguration(OnlineConfigurationDao dao) {
        this.dao = dao;
    }

    public String getServerLocale() {
        String value = dao.getConfigurationOption(OnlineConfigurationOptionName.SERVER_LOCALE);
        return StringUtils.isEmpty(value) ? ConfigurationDefaults.SERVER_LOCALE : value;
    }

    public long getLastComponentId() {
        String value = dao.getConfigurationOption(OnlineConfigurationOptionName.LAST_COMPONENT_ID);
        return StringUtils.isEmpty(value) ? ConfigurationDefaults.LAST_COMPONENT_ID : Long.parseLong(value);
    }

    public int getTelnetPort() {
        String value = dao.getConfigurationOption(OnlineConfigurationOptionName.TELNET_PORT);
        return StringUtils.isEmpty(value) ? ConfigurationDefaults.TELNET_PORT : Integer.parseInt(value);
    }
}
