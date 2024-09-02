package org.mumue.mumue.player;

import java.sql.Timestamp;
import jakarta.inject.Inject;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.mumue.mumue.Repository;
import org.mumue.mumue.database.DatabaseAccessor;

public class PlayerRepository implements Repository<Player> {
    private final DatabaseAccessor database;

    @Inject
    public PlayerRepository(DatabaseAccessor database) {
        this.database = database;
    }

    @Override
    public Player get(long id) {
        ResultSetHandler<Player> resultSetHandler = new BeanHandler<>(Player.class, new PlayerRowProcessor());
        Player player = database.query("select * from players where id = ?", resultSetHandler, id);
        return player == null ? new Player() : player;
    }

    public Player get(String loginId) {
        ResultSetHandler<Player> resultSetHandler = new BeanHandler<>(Player.class, new PlayerRowProcessor());
        Player player = database.query("select * from players where loginId = ?", resultSetHandler, loginId);
        return player == null ? new Player() : player;
    }

    public Player get(String loginId, String password) {
        ResultSetHandler<Player> resultSetHandler = new BeanHandler<>(Player.class, new PlayerRowProcessor());
        Player player = database.query("select * from players where loginId = ? and password = ?", resultSetHandler, loginId, password);
        return player == null ? new Player() : player;
    }

    @Override
    public void save(Player player) {
        database.update("update players SET locale=?, lastUsed=?, lastModified=?, useCount=?, administrator=?",
                player.getLocale(),
                Timestamp.from(player.getLastUsed()), Timestamp.from(player.getLastModified()),
                player.getUseCount(), player.isAdministrator()
        );
    }

    public void add(Player player, String password) {
        database.update("insert into players (loginId, password, locale, created, lastUsed, lastModified, useCount, administrator) values (?, ?, ?, ?, ?, ?, ?, ?)",
                player.getLoginId(), password, player.getLocale(),
                Timestamp.from(player.getCreated()), Timestamp.from(player.getLastUsed()), Timestamp.from(player.getLastModified()),
                player.getUseCount(), player.isAdministrator()
        );
    }

    @Override
    public void add(Player player) {
        throw new UnsupportedOperationException();
    }
}
