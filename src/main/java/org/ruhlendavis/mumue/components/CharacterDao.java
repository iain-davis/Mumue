package org.ruhlendavis.mumue.components;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import org.ruhlendavis.mumue.database.QueryRunnerProvider;

public class CharacterDao {
    private static final String GET_BY_UNIVERSE_QUERY = "select * from characters where name = ? and universeId = ?";

    public GameCharacter getCharacter(String name, long universeId) {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler<GameCharacter> resultSetHandler = new BeanHandler<>(GameCharacter.class, new CharacterRowProcessor());
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

    private static final String GET_BY_NAME_QUERY = "select * from characters where name = ?";
    public GameCharacter getCharacter(String name) {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler<GameCharacter> resultSetHandler = new BeanHandler<>(GameCharacter.class, new CharacterRowProcessor());
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

    private static final String INSERT_QUERY = "insert into characters (id, name, description, created, lastUsed, lastModified, useCount, locationId, universeId, playerId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public void addCharacter(GameCharacter character) {
        QueryRunner database = QueryRunnerProvider.get();
        try {
            database.update(INSERT_QUERY, character.getId(), character.getName(), character.getDescription(),
                    Timestamp.from(character.getCreated()), Timestamp.from(character.getLastUsed()), Timestamp.from(character.getLastModified()), character.getUseCount(),
                    character.getLocationId(), character.getUniverseId(), character.getPlayerId()
            );
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static final String GET_BY_PLAYER_ID_QUERY = "select * from characters where playerId = ?";
    public List<GameCharacter> getCharacters(long playerId) {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler<List<GameCharacter>> resultSetHandler = new BeanListHandler<>(GameCharacter.class, new CharacterRowProcessor());
        try {
            return database.query(GET_BY_PLAYER_ID_QUERY, resultSetHandler, playerId);
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static final String GET_BY_ID_QUERY = "select * from characters where id = ?";
    public GameCharacter getCharacter(long id) {
        QueryRunner database = QueryRunnerProvider.get();
        ResultSetHandler<GameCharacter> resultSetHandler = new BeanHandler<>(GameCharacter.class, new CharacterRowProcessor());
        try {
            GameCharacter character = database.query(GET_BY_ID_QUERY, resultSetHandler, id);
            if (character == null) {
                return new GameCharacter();
            }
            return character;
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }
}
