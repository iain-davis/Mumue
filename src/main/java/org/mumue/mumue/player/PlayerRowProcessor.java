package org.mumue.mumue.player;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.BasicRowProcessor;

import org.mumue.mumue.components.ComponentBaseResultSetProcessor;

public class PlayerRowProcessor extends BasicRowProcessor {
    private ComponentBaseResultSetProcessor resultSetProcessor = new ComponentBaseResultSetProcessor();
    @Override
    public <T> T toBean(ResultSet resultSet, Class<T> type) throws SQLException {
        Player player = new Player();
        player.setLoginId(resultSet.getString("loginId"));
        player.setLocale(resultSet.getString("locale"));
        player.setAdministrator(resultSet.getBoolean("administrator"));
        resultSetProcessor.process(resultSet, player);
        return type.cast(player);
    }
}
