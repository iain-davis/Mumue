package org.mumue.mumue.player;

import java.sql.Timestamp;
import javax.inject.Inject;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.mumue.mumue.database.DatabaseAccessor;

public class PlayerDao {
    private static final String AUTHENTICATION_QUERY = "select count(*) from players where loginId = ? and password = ?";
    private static final String GET_BY_LOGIN_QUERY = "select * from players where loginId = ? and password = ?";
    private static final String GET_BY_ID_QUERY = "select * from players where id = ?";
    private static final String INSERT_QUERY = "insert into players (loginId, password, locale, created, lastUsed, lastModified, useCount, administrator) values (?, ?, ?, ?, ?, ?, ?, ?)";
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

    public Player getPlayer(long playerId) {
        ResultSetHandler<Player> resultSetHandler = new BeanHandler<>(Player.class, new PlayerRowProcessor());
        Player player = database.query(GET_BY_ID_QUERY, resultSetHandler, playerId);
        if (player == null) {
            return new Player();
        }
        return player;
    }

    public Player getPlayer(String login, String password) {
        ResultSetHandler<Player> resultSetHandler = new BeanHandler<>(Player.class, new PlayerRowProcessor());
        Player player = database.query(GET_BY_LOGIN_QUERY, resultSetHandler, login, password);
        if (player == null) {
            return new Player();
        }
        return player;
    }

    public boolean playerExistsFor(String loginId) {
        ResultSetHandler<Player> resultSetHandler = new BeanHandler<>(Player.class, new PlayerRowProcessor());
        Player player = database.query(GET_QUERY_BY_LOGIN_ID, resultSetHandler, loginId);
        return player != null;
    }

    public void createPlayer(Player player, String password) {
        database.update(INSERT_QUERY, player.getLoginId(), password, player.getLocale(),
                Timestamp.from(player.getCreated()), Timestamp.from(player.getLastUsed()), Timestamp.from(player.getLastModified()), player.getUseCount(),
                player.isAdministrator()
        );
    }
}
