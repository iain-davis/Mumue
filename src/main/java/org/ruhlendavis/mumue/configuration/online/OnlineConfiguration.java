package org.ruhlendavis.mumue.configuration.online;

import org.apache.commons.lang3.StringUtils;

import org.ruhlendavis.mumue.configuration.ConfigurationDefaults;

public class OnlineConfiguration {
    private OnlineConfigurationDao dao = new OnlineConfigurationDao();

    public String getServerLocale() {
        String value = dao.getConfigurationOption(OnlineConfigurationOptionName.SERVER_LOCALE);
        return StringUtils.isBlank(value) ? ConfigurationDefaults.SERVER_LOCALE : value;
    }

    public long getNextComponentId() {
        String value = dao.getConfigurationOption(OnlineConfigurationOptionName.NEXT_COMPONENT_ID);
        return StringUtils.isBlank(value) ? ConfigurationDefaults.NEXT_COMPONENT_ID : Long.parseLong(value);
    }
}
