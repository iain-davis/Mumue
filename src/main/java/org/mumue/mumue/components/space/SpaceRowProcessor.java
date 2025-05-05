package org.mumue.mumue.components.space;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.mumue.mumue.components.LocatableComponentResultSetProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpaceRowProcessor extends BasicRowProcessor {
    private final LocatableComponentResultSetProcessor processor;

    public SpaceRowProcessor() {
        this(new LocatableComponentResultSetProcessor());
    }

    SpaceRowProcessor(LocatableComponentResultSetProcessor processor) {
        this.processor = processor;
    }

    @Override
    public <T> T toBean(ResultSet resultSet, Class<? extends T> type) throws SQLException {
        Space space = new Space();
        processor.process(resultSet, space);

        return type.cast(space);
    }
}
