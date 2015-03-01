package org.ruhlendavis.mumue.components.character;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;

import org.ruhlendavis.mumue.components.LocatableComponentResultSetProcessor;

public class CharacterRowProcessor extends BasicRowProcessor {
    LocatableComponentResultSetProcessor componentResultSetProcessor = new LocatableComponentResultSetProcessor();

    @Override
    public <T> T toBean(ResultSet resultSet, Class<T> type) throws SQLException {
        GameCharacter character = new GameCharacter();
        character.setPlayerId(resultSet.getLong("playerId"));
        componentResultSetProcessor.process(resultSet, character);
        return type.cast(character);
    }


    @Override
    public <T> List<T> toBeanList(ResultSet resultSet, Class<T> type) throws SQLException {
        try {
            List<T> characters = new LinkedList<>();
            while (resultSet.next()) {
                characters.add(toBean(resultSet, type));
            }
            return characters;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
