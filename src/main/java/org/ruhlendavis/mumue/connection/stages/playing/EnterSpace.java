package org.ruhlendavis.mumue.connection.stages.playing;

import org.ruhlendavis.mumue.components.space.Space;
import org.ruhlendavis.mumue.components.space.SpaceDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;

public class EnterSpace implements ConnectionStage {
    private SpaceDao spaceDao = new SpaceDao();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        Space space = spaceDao.getSpace(connection.getCharacter().getLocationId());
        String text= space.getName() + "\\r\\n" + space.getDescription();
        connection.getOutputQueue().push(text);
        return new PlayCharacter();
    }
}
