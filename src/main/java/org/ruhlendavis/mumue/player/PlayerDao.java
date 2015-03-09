package org.ruhlendavis.mumue.player;

import java.sql.Timestamp;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.ruhlendavis.mumue.database.DatabaseAccessor;
import org.ruhlendavis.mumue.database.DatabaseAccessorProvider;

public class PlayerDao {
    private static final String AUTHENTICATION_QUERY = "select count(*) from players where loginId = ? and password = ?";
    private static final String GET_QUERY = "select * from players where loginId = ? and password = ?";
    private static final String INSERT_QUERY = "insert into players (loginId, password, locale, created, lastUsed, lastModified, useCount, administrator) values (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_QUERY_BY_LOGIN_ID = "select * from players where loginId = ?";

    public boolean authenticate(String login, String password) {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler resultSetHandler = new ScalarHandler<>(1);
        long count = (Long) database.query(AUTHENTICATION_QUERY, resultSetHandler, login, password);
        return count == 1;
    }

    public Player getPlayer(String login, String password) {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler<Player> resultSetHandler = new BeanHandler<>(Player.class, new PlayerRowProcessor());
        Player player = database.query(GET_QUERY, resultSetHandler, login, password);
        if (player == null) {
            return new Player();
        }
        return player;
    }

    public boolean playerExistsFor(String loginId) {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler<Player> resultSetHandler = new BeanHandler<>(Player.class, new PlayerRowProcessor());
        Player player = database.query(GET_QUERY_BY_LOGIN_ID, resultSetHandler, loginId);
        return player != null;
    }

    public void createPlayer(Player player, String password) {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        database.update(INSERT_QUERY, player.getLoginId(), password, player.getLocale(),
                Timestamp.from(player.getCreated()), Timestamp.from(player.getLastUsed()), Timestamp.from(player.getLastModified()), player.getUseCount(),
                player.isAdministrator()
        );
    }
}
