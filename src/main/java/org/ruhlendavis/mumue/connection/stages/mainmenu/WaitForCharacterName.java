package org.ruhlendavis.mumue.connection.stages.mainmenu;

import org.ruhlendavis.mumue.components.GameCharacter;
import org.ruhlendavis.mumue.components.GameCharacterDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.importer.GlobalConstants;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class WaitForCharacterName implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();
    private GameCharacterDao dao = new GameCharacterDao();

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

        if (nameTakenByOtherPlayer(name, connection.getPlayer().getLoginId())) {
            String text = textMaker.getText(TextName.CharacterNameTakenByOtherPlayer, connection.getPlayer().getLocale(), configuration.getServerLocale());
            connection.getOutputQueue().push(text);
            return new CharacterNamePrompt();
        }

        connection.getCharacter().setName(name);
        return new CreateCharacter();
    }

    private boolean nameTakenByOtherPlayer(String name, String loginId) {
        return !loginId.equals(dao.getCharacter(name).getPlayerId());
    }

    private boolean nameTakenInUniverse(String name, long universeId) {
        GameCharacter character = dao.getCharacter(name, universeId);
        return !(character.getId() == GlobalConstants.REFERENCE_UNKNOWN);

    }
}
