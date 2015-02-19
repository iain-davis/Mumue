package org.ruhlendavis.meta.player;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.ruhlendavis.meta.database.QueryRunnerProvider;
import org.ruhlendavis.meta.importer.GlobalConstants;

public class PlayerDao {
    private static final String AUTHENTICATION_QUERY = "select count(*) from players where loginId = ? and password = ?";
    private static final String GET_QUERY = "select * from players where loginId = ? and password = ?";

    public boolean authenticate(String login, String password) {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler resultSetHandler = new ScalarHandler<>(1);

        try {
            long count = (Long) database.query(AUTHENTICATION_QUERY, resultSetHandler, login, password);
            return count == 1;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Player getPlayer(String login, String password) {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler<Player> resultSetHandler = new BeanHandler<>(Player.class);
        try {
            Player player = database.query(GET_QUERY, resultSetHandler, login, password);
            if (player == null) {
                return new Player().withId(GlobalConstants.REFERENCE_NOT_FOUND);
            }
            return player;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
