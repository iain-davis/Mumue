package org.ruhlendavis.meta.player;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.ruhlendavis.meta.database.QueryRunnerProvider;

public class PlayerAuthenticationDao {
    public static final String PLAYER_AUTHENTICATION_QUERY = "select count(*) from players where login = ? and password = ?";

    public boolean authenticate(String login, String password) {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler resultSetHandler = new ScalarHandler<>(1);

        try {
            long count = (Long) database.query(PLAYER_AUTHENTICATION_QUERY, resultSetHandler, login, password);
            return count == 1;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
