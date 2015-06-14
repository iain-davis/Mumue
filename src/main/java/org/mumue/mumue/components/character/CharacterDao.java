package org.mumue.mumue.components.character;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseAccessorProvider;

public class CharacterDao {
    private static final String GET_BY_UNIVERSE_QUERY = "select * from characters where name = ? and universeId = ?";

    public GameCharacter getCharacter(String name, long universeId) {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler<GameCharacter> resultSetHandler = new BeanHandler<>(GameCharacter.class, new CharacterRowProcessor());
        GameCharacter character = database.query(GET_BY_UNIVERSE_QUERY, resultSetHandler, name, universeId);
        if (character == null) {
            return new GameCharacter();
        }
        return character;
    }

    private static final String GET_BY_NAME_QUERY = "select * from characters where name = ?";
    public GameCharacter getCharacter(String name) {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler<GameCharacter> resultSetHandler = new BeanHandler<>(GameCharacter.class, new CharacterRowProcessor());
        GameCharacter character = database.query(GET_BY_NAME_QUERY, resultSetHandler, name);
        if (character == null) {
            return new GameCharacter();
        }
        return character;
    }

    private static final String INSERT_QUERY = "insert into characters (id, name, description, created, lastUsed, lastModified, useCount, locationId, universeId, playerId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    public void createCharacter(GameCharacter character) {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        database.update(INSERT_QUERY, character.getId(), character.getName(), character.getDescription(),
                Timestamp.from(character.getCreated()), Timestamp.from(character.getLastUsed()), Timestamp.from(character.getLastModified()), character.getUseCount(),
                character.getLocationId(), character.getUniverseId(), character.getPlayerId()
        );
    }

    private static final String GET_BY_PLAYER_ID_QUERY = "select * from characters where playerId = ?";

    public List<GameCharacter> getCharacters(long playerId) {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler<List<GameCharacter>> resultSetHandler = new BeanListHandler<>(GameCharacter.class, new CharacterRowProcessor());
        return database.query(GET_BY_PLAYER_ID_QUERY, resultSetHandler, playerId);
    }

    private static final String GET_BY_ID_QUERY = "select * from characters where id = ?";

    public GameCharacter getCharacter(long id) {
        DatabaseAccessor database = DatabaseAccessorProvider.get();
        ResultSetHandler<GameCharacter> resultSetHandler = new BeanHandler<>(GameCharacter.class, new CharacterRowProcessor());
        GameCharacter character = database.query(GET_BY_ID_QUERY, resultSetHandler, id);
        if (character == null) {
            return new GameCharacter();
        }
        return character;
    }
}
