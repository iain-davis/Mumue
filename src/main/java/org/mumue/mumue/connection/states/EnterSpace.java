package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.components.space.Space;
import org.mumue.mumue.components.space.SpaceDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.importer.GlobalConstants;

public class EnterSpace implements ConnectionState {
    private final PlayCharacter playCharacter;
    private final SpaceDao spaceDao;

    @Inject
    @Singleton
    public EnterSpace(PlayCharacter playCharacter, SpaceDao spaceDao) {
        this.playCharacter = playCharacter;
        this.spaceDao = spaceDao;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        Space space = spaceDao.getSpace(connection.getCharacter().getLocationId());
        String text = space.getName() + GlobalConstants.TCP_LINE_SEPARATOR + space.getDescription() + GlobalConstants.TCP_LINE_SEPARATOR;
        connection.getOutputQueue().push(text);
        return playCharacter;
    }
}
