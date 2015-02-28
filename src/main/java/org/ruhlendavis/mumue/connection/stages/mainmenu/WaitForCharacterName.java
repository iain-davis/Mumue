package org.ruhlendavis.mumue.connection.stages.mainmenu;

import org.ruhlendavis.mumue.components.CharacterDao;
import org.ruhlendavis.mumue.components.GameCharacter;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.importer.GlobalConstants;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class WaitForCharacterName implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();
    private CharacterDao dao = new CharacterDao();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }

        String name = connection.getInputQueue().pop();

        if (nameTakenInUniverse(name, connection.getCharacter().getUniverseId())) {
            String text = textMaker.getText(TextName.CharacterNameAlreadyExists, connection.getPlayer().getLocale(), configuration.getServerLocale());
            connection.getOutputQueue().push(text);
            return new CharacterNamePrompt();
        }

        if (nameTakenByOtherPlayer(name, connection.getPlayer().getId())) {
            String text = textMaker.getText(TextName.CharacterNameTakenByOtherPlayer, connection.getPlayer().getLocale(), configuration.getServerLocale());
            connection.getOutputQueue().push(text);
            return new CharacterNamePrompt();
        }

        connection.getCharacter().setName(name);
        connection.getCharacter().setId(configuration.getNewComponentId());
        connection.getCharacter().setPlayerId(connection.getPlayer().getId());
        dao.addCharacter(connection.getCharacter());
        return new DisplayPlayerMenu();
    }

    private boolean nameTakenByOtherPlayer(String name, long playerId) {
        GameCharacter character = dao.getCharacter(name);
        return character.getId() != GlobalConstants.REFERENCE_UNKNOWN && character.getPlayerId() != playerId;
    }

    private boolean nameTakenInUniverse(String name, long universeId) {
        GameCharacter character = dao.getCharacter(name, universeId);
        return character.getId() != GlobalConstants.REFERENCE_UNKNOWN;

    }
}
