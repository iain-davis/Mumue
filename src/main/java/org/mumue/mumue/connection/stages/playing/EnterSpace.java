package org.mumue.mumue.connection.stages.playing;

import org.mumue.mumue.components.space.SpaceDao;
import org.mumue.mumue.components.space.Space;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;

public class EnterSpace implements ConnectionStage {
    private SpaceDao spaceDao = new SpaceDao();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        Space space = spaceDao.getSpace(connection.getCharacter().getLocationId());
        String text = space.getName() + "\\r\\n" + space.getDescription() + "\\r\\n";
        connection.getOutputQueue().push(text);
        return new PlayCharacter();
    }
}
