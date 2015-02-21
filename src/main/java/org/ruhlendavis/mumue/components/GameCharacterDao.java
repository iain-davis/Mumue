package org.ruhlendavis.mumue.components;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import org.ruhlendavis.mumue.database.QueryRunnerProvider;

public class GameCharacterDao {
    private static final String GET_QUERY = "select * from characters where name = ? and universeId = ?";

    public GameCharacter getCharacter(String name, long universeId) {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler<GameCharacter> resultSetHandler = new BeanHandler<>(GameCharacter.class, new GameCharacterRowProcessor());
        try {
            GameCharacter character = database.query(GET_QUERY, resultSetHandler, name, universeId);
            if (character == null) {
                return new GameCharacter();
            }
            return character;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
