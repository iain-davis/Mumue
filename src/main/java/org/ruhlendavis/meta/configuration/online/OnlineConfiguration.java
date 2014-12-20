package org.ruhlendavis.meta.configuration.online;

import org.apache.commons.lang3.StringUtils;

import org.ruhlendavis.meta.configuration.ConfigurationDefaults;

public class OnlineConfiguration {
    private final OnlineConfigurationDao dao;

    public OnlineConfiguration(OnlineConfigurationDao dao) {
        this.dao = dao;
    }

    public String getServerLocale() {
        String value = dao.getConfigurationOption(OnlineConfigurationOptionName.SERVER_LOCALE);
        return StringUtils.isBlank(value) ? ConfigurationDefaults.SERVER_LOCALE : value;
    }
}
