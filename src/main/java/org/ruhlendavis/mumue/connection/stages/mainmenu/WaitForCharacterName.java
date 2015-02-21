package org.ruhlendavis.mumue.connection.stages.mainmenu;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;

public class WaitForCharacterName implements ConnectionStage {
    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }
        connection.getCharacter().setName(connection.getInputQueue().pop());
        return new CreateCharacter();
    }
}
