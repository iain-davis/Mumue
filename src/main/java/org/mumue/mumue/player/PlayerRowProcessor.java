package org.mumue.mumue.player;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.mumue.mumue.components.ComponentResultSetProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerRowProcessor extends BasicRowProcessor {
    private final ComponentResultSetProcessor resultSetProcessor;

    public PlayerRowProcessor() {
        this(new ComponentResultSetProcessor());
    }

    PlayerRowProcessor(ComponentResultSetProcessor componentResultSetProcessor) {
        this.resultSetProcessor = componentResultSetProcessor;
    }

    @Override
    public <T> T toBean(ResultSet resultSet, Class<? extends T> type) throws SQLException {
        Player player = new Player();
        player.setLoginId(resultSet.getString("loginId"));
        player.setLocale(resultSet.getString("locale"));
        player.setAdministrator(resultSet.getBoolean("administrator"));
        resultSetProcessor.process(resultSet, player);
        return type.cast(player);
    }
}
