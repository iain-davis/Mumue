package org.ruhlendavis.mumue.connection.stages.mainmenu;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;

public class PlayCharacter implements ConnectionStage {
    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        return new PlayCharacter();
    }
}
