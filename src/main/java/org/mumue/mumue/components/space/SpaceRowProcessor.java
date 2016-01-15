package org.mumue.mumue.components.space;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.mumue.mumue.components.LocatableComponentResultSetProcessor;

public class SpaceRowProcessor extends BasicRowProcessor {
    private LocatableComponentResultSetProcessor processor = new LocatableComponentResultSetProcessor();

    @Override
    public <T> T toBean(ResultSet resultSet, Class<T> type) throws SQLException {
        Space space = new Space();
        processor.process(resultSet, space);

        return type.cast(space);
    }
}
