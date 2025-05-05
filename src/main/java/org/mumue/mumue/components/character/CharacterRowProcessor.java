package org.mumue.mumue.components.character;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.mumue.mumue.components.LocatableComponentResultSetProcessor;

public class CharacterRowProcessor extends BasicRowProcessor {
    private final LocatableComponentResultSetProcessor componentResultSetProcessor;

    public CharacterRowProcessor() {
        this(new LocatableComponentResultSetProcessor());
    }

    CharacterRowProcessor(LocatableComponentResultSetProcessor locatableComponentResultSetProcessor) {
        this.componentResultSetProcessor = locatableComponentResultSetProcessor;
    }

    @Override
    public <T> T toBean(ResultSet resultSet, Class<? extends T> type) throws SQLException {
        GameCharacter character = new GameCharacter();
        character.setPlayerId(resultSet.getLong("playerId"));
        componentResultSetProcessor.process(resultSet, character);
        return type.cast(character);
    }

    @Override
    public <T> List<T> toBeanList(ResultSet resultSet, Class<? extends T> type) throws SQLException {
        List<T> characters = new LinkedList<>();
        while (resultSet.next()) {
            characters.add(toBean(resultSet, type));
        }
        return characters;
    }
}
