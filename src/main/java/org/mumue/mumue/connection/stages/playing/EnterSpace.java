package org.mumue.mumue.connection.stages.playing;

import com.google.inject.Injector;
import org.mumue.mumue.components.space.SpaceDao;
import org.mumue.mumue.components.space.Space;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;

import javax.inject.Inject;

public class EnterSpace implements ConnectionStage {
    private final Injector injector;
    private final SpaceDao spaceDao;

    @Inject
    public EnterSpace(Injector injector, SpaceDao spaceDao) {
        this.injector = injector;
        this.spaceDao = spaceDao;
    }

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        Space space = spaceDao.getSpace(connection.getCharacter().getLocationId());
        String text = space.getName() + "\\r\\n" + space.getDescription() + "\\r\\n";
        connection.getOutputQueue().push(text);
        return injector.getInstance(PlayCharacter.class);
    }
}
