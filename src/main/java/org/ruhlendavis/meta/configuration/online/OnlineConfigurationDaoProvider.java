package org.ruhlendavis.meta.configuration.online;

import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

public class OnlineConfigurationDaoProvider {
    public Dao<ConfigurationOption, String> get(ConnectionSource connectionSource) {
        try {
            return DaoManager.createDao(connectionSource, ConfigurationOption.class);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
