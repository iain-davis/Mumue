package org.ruhlendavis.mumue.connection.stages.mainmenu;

import org.ruhlendavis.mumue.components.GameCharacter;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.connection.stages.NoOperationStage;

public class CreateCharacter implements ConnectionStage {
    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        GameCharacter character = new GameCharacter();
        character.setName(connection.getInputQueue().pop());
        connection.getPlayer().getCharacters().add(character);
        return new NoOperationStage();
    }
}
