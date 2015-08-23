package org.mumue.mumue.database;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.mumue.mumue.configuration.Configuration;

import javax.sql.DataSource;

public class DataSourceFactory {
    public DataSource create(Configuration configuration) {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName(SqlConstants.DRIVER_CLASS_NAME);
        source.setUsername(configuration.getDatabaseUsername());
        source.setPassword(configuration.getDatabasePassword());
        if (StringUtils.isBlank(configuration.getDatabaseUrl())) {
            source.setUrl("jdbc:h2:" + configuration.getDatabasePath() + ";MV_STORE=FALSE;MVCC=FALSE");
        } else {
            source.setUrl(configuration.getDatabaseUrl());
        }
        return source;
    }
}
