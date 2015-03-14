package org.ruhlendavis.mumue.configuration.online;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import org.ruhlendavis.mumue.configuration.ConfigurationDefaults;

public class OnlineConfiguration {
    private final OnlineConfigurationDao dao;

    @Inject
    public OnlineConfiguration(OnlineConfigurationDao dao) {
        this.dao = dao;
    }

    public String getServerLocale() {
        String value = dao.getConfigurationOption(OnlineConfigurationOptionName.SERVER_LOCALE);
        return StringUtils.isBlank(value) ? ConfigurationDefaults.SERVER_LOCALE : value;
    }

    public long getLastComponentId() {
        String value = dao.getConfigurationOption(OnlineConfigurationOptionName.LAST_COMPONENT_ID);
        return StringUtils.isBlank(value) ? ConfigurationDefaults.LAST_COMPONENT_ID : Long.parseLong(value);
    }
}
