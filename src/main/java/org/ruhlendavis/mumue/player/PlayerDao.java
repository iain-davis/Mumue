package org.ruhlendavis.mumue.player;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.ruhlendavis.mumue.database.DatabaseAccessor;
import org.ruhlendavis.mumue.database.DatabaseAccessorProvider;

public class PlayerDao {
    private static final String AUTHENTICATION_QUERY = "select count(*) from players where loginId = ? and password = ?";
    private static final String GET_QUERY = "select * from players where loginId = ? and password = ?";

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
}
