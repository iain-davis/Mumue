package org.mumue.mumue.player;

import javax.inject.Inject;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.mumue.mumue.database.DatabaseAccessor;

public class PlayerDao {
    private static final String AUTHENTICATION_QUERY = "select count(*) from players where loginId = ? and password = ?";
    private static final String GET_QUERY_BY_LOGIN_ID = "select * from players where loginId = ?";
    private final DatabaseAccessor database;

    @Inject
    public PlayerDao(DatabaseAccessor database) {
        this.database = database;
    }

    public boolean authenticate(String login, String password) {
        ResultSetHandler resultSetHandler = new ScalarHandler<>(1);
        long count = (Long) database.query(AUTHENTICATION_QUERY, resultSetHandler, login, password);
        return count == 1;
    }

    public boolean playerExistsFor(String loginId) {
        ResultSetHandler<Player> resultSetHandler = new BeanHandler<>(Player.class, new PlayerRowProcessor());
        Player player = database.query(GET_QUERY_BY_LOGIN_ID, resultSetHandler, loginId);
        return player != null;
    }
}
