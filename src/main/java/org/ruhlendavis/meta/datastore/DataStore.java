package org.ruhlendavis.meta.datastore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.ruhlendavis.meta.configuration.Configuration;

public class DataStore {
    private Connection connection;

    public void setupConnection(Configuration configuration) {
        try {
            String uri = "jdbc:h2:" + configuration.getDatabasePath() + ";MV_STORE=FALSE;MVCC=FALSE";
            connection = DriverManager.getConnection(uri, configuration.getDatabaseUsername(), configuration.getDatabasePassword());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean isDataStoreReady() {
        return false;
    }

    public void setupDatabase(Configuration configuration) {
        String query;
        try {
            Statement statement = connection.createStatement();
            query = "select count(*) from information_schema.tables where table_name = 'CONFIGURATION_OPTIONS'";
            ResultSet resultSet = statement.executeQuery(query);
            resultSet.next();
            if (resultSet.getBoolean(1)) {
                return;
            }

            query = "RUNSCRIPT FROM 'classpath:org/ruhlendavis/meta/datastore/schema.sql'";
            if (statement.execute(query)) {
                query = "RUNSCRIPT FROM 'classpath:org/ruhlendavis/meta/datastore/defaultData.sql'";
                if (statement.execute(query)) {
                    return;
                } else {
                    System.out.println("CRITICAL: Error while loading default database data.");
                }
            } else {
                System.out.println("CRITICAL: Error while loading database schema.");
            }

            // error message?
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
//
//    public boolean write(Player player) {
//        return false;
//    }
}
