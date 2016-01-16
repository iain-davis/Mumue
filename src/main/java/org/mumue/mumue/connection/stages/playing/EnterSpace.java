package org.mumue.mumue.connection.stages.playing;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.components.space.Space;
import org.mumue.mumue.components.space.SpaceDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;

public class EnterSpace implements ConnectionStage {
    private final Injector injector;
    private final SpaceDao spaceDao;

    @Inject
    public EnterSpace(Injector injector, SpaceDao spaceDao) {
        this.injector = injector;
        this.spaceDao = spaceDao;
    }

    @Override
    public ConnectionStage execute(Connection connection, ApplicationConfiguration configuration) {
        Space space = spaceDao.getSpace(connection.getCharacter().getLocationId());
        String text = space.getName() + "\\r\\n" + space.getDescription() + "\\r\\n";
        connection.getOutputQueue().push(text);
        return injector.getInstance(PlayCharacter.class);
    }
}
