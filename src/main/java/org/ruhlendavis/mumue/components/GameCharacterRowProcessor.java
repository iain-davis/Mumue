package org.ruhlendavis.mumue.components;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.BasicRowProcessor;

public class GameCharacterRowProcessor extends BasicRowProcessor {
    LocatableComponentResultSetProcessor componentResultSetProcessor = new LocatableComponentResultSetProcessor();
    @Override
    public <T> T toBean(ResultSet resultSet, Class<T> type) throws SQLException {
        GameCharacter character = new GameCharacter();
        componentResultSetProcessor.process(resultSet, character);
        return type.cast(character);
    }
}
