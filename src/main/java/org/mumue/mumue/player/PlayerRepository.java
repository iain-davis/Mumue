package org.mumue.mumue.player;

import java.sql.Timestamp;
import javax.inject.Inject;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.mumue.mumue.Repository;
import org.mumue.mumue.Specification;
import org.mumue.mumue.database.DatabaseAccessor;

public class PlayerRepository implements Repository<Player> {
    private static final String UPDATE_QUERY = "update players SET loginId=?, locale=?, lastUsed=?, lastModified=?, useCount=?, administrator=?";
    private final DatabaseAccessor database;

    @Inject
    public PlayerRepository(DatabaseAccessor database) {
        this.database = database;
    }

    @Override
    public Player get(long id) {
        PlayerSpecification playerSpecification = new PlayerSpecification();
        playerSpecification.setId(id);
        return get(playerSpecification);
    }

    @Override
    public Player get(Specification<Player> specification) {
        PlayerSpecification playerSpecification = (PlayerSpecification) specification;
        ResultSetHandler<Player> resultSetHandler = new BeanHandler<>(Player.class, new PlayerRowProcessor());
        Player player = database.query("select * from players where id = ?", resultSetHandler, playerSpecification.getId());
        return player == null ? new Player() : player;
    }

    @Override
    public void save(Player player) {
        database.update(UPDATE_QUERY, player.getLoginId(), player.getLocale(),
                Timestamp.from(player.getLastUsed()), Timestamp.from(player.getLastModified()),
                player.getUseCount(), player.isAdministrator()
        );
    }
}
