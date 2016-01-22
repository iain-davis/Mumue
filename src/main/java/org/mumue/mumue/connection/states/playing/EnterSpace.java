package org.mumue.mumue.connection.states.playing;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.components.space.Space;
import org.mumue.mumue.components.space.SpaceDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.states.ConnectionState;
import org.mumue.mumue.importer.GlobalConstants;

public class EnterSpace implements ConnectionState {
    private final Injector injector;
    private final SpaceDao spaceDao;

    @Inject
    public EnterSpace(Injector injector, SpaceDao spaceDao) {
        this.injector = injector;
        this.spaceDao = spaceDao;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        Space space = spaceDao.getSpace(connection.getCharacter().getLocationId());
        String text = space.getName() + GlobalConstants.TCP_LINE_SEPARATOR + space.getDescription() + GlobalConstants.TCP_LINE_SEPARATOR;
        connection.getOutputQueue().push(text);
        return injector.getInstance(PlayCharacter.class);
    }
}
