package org.ruhlendavis.mumue.components;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;

public class UniverseRowProcessor extends BasicRowProcessor {
    private TimestampAbleResultSetProcessor processor = new TimestampAbleResultSetProcessor();

    @Override
    public <T> T toBean(ResultSet resultSet, Class<T> type) throws SQLException {
        Universe universe = new Universe();
        universe.setId(resultSet.getLong("id"));
        universe.setName(resultSet.getString("name"));
        universe.setDescription(resultSet.getString("description"));
        processor.process(resultSet, universe);

        return type.cast(universe);
    }

    @Override
    public <T> List<T> toBeanList(ResultSet resultSet, Class<T> type) throws SQLException {
        try {
            List<T> universes = new LinkedList<>();
            while (resultSet.next()) {
                universes.add(toBean(resultSet, type));
            }
            return universes;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
