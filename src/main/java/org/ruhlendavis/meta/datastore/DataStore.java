package org.ruhlendavis.meta.datastore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.ruhlendavis.meta.configuration.Configuration;

public class DataStore {
    private Connection connection;

    public void setup(Configuration configuration) {
        try {
            String uri = "jdbc:h2:" + configuration.getDatabasePath() + ";MV_STORE=FALSE;MVCC=FALSE";
            connection = DriverManager.getConnection(uri, configuration.getDatabaseUsername(), configuration.getDatabasePassword());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void populateDatabase() {
        String query;
        try {
            Statement statement = connection.createStatement();
            query = "RUNSCRIPT FROM 'classpath:org/ruhlendavis/meta/datastore/schema.sql'";
            statement.execute(query);
            query = "RUNSCRIPT FROM 'classpath:org/ruhlendavis/meta/datastore/defaultData.sql'";
            statement.execute(query);
        } catch (SQLException exception) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            exception.printStackTrace();
        }
    }

    public boolean isDatabaseEmpty() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(*) from information_schema.tables where table_name = 'CONFIGURATION_OPTIONS'");
            resultSet.next();
            if (resultSet.getBoolean(1)) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
//
//    public boolean write(Player player) {
//        return false;
//    }
}
