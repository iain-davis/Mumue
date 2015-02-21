package org.ruhlendavis.mumue.player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;

import org.ruhlendavis.mumue.components.TimestampAbleResultSetProcessor;

public class PlayerRowProcessor extends BasicRowProcessor {
    private TimestampAbleResultSetProcessor resultSetProcessor = new TimestampAbleResultSetProcessor();
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
