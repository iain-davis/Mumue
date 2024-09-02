package org.mumue.mumue.configuration.online;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.mumue.mumue.configuration.ConfigurationDefaults;

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
}
