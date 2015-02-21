package org.ruhlendavis.mumue.components;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import org.ruhlendavis.mumue.database.QueryRunnerProvider;

public class GameCharacterDao {
    private static final String GET_BY_UNIVERSE_QUERY = "select * from characters where name = ? and universeId = ?";
    private static final String GET_BY_NAME_QUERY = "select * from characters where name = ?";

    public GameCharacter getCharacter(String name, long universeId) {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler<GameCharacter> resultSetHandler = new BeanHandler<>(GameCharacter.class, new GameCharacterRowProcessor());
        try {
            GameCharacter character = database.query(GET_BY_UNIVERSE_QUERY, resultSetHandler, name, universeId);
            if (character == null) {
                return new GameCharacter();
            }
            return character;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public GameCharacter getCharacter(String name) {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler<GameCharacter> resultSetHandler = new BeanHandler<>(GameCharacter.class, new GameCharacterRowProcessor());
        try {
            GameCharacter character = database.query(GET_BY_NAME_QUERY, resultSetHandler, name);
            if (character == null) {
                return new GameCharacter();
            }
            return character;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
