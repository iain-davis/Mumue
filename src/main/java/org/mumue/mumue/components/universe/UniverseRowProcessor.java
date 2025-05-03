package org.mumue.mumue.components.universe;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.mumue.mumue.components.GameComponentResultSetProcessor;

public class UniverseRowProcessor extends BasicRowProcessor {
    private GameComponentResultSetProcessor processor = new GameComponentResultSetProcessor();

    @Override
    public <T> T toBean(ResultSet resultSet, Class<? extends T> type) throws SQLException {
        Universe universe = new Universe();
        universe.setStartingSpaceId(resultSet.getLong("startingSpaceId"));
        processor.process(resultSet, universe);

        return type.cast(universe);
    }

    @Override
    public <T> List<T> toBeanList(ResultSet resultSet, Class<? extends T> type) throws SQLException {
        List<T> universes = new LinkedList<>();
        while (resultSet.next()) {
            universes.add(toBean(resultSet, type));
        }
        return universes;
    }
}
