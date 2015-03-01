package org.ruhlendavis.mumue.connection.stages.mainmenu;

import org.ruhlendavis.mumue.components.character.CharacterDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.connection.stages.playing.EnterUniverse;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class WaitForCharacterSelection implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();
    private CharacterDao dao = new CharacterDao();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        if (connection.getInputQueue().isEmpty()) {
            return this;
        }
        String selection = connection.getInputQueue().pop();
        Long characterId = connection.getMenuOptionIds().get(selection);
        if (characterId == null) {
            String text = textMaker.getText(TextName.InvalidOption, connection.getPlayer().getLocale(), configuration.getServerLocale());
            connection.getOutputQueue().push(text);
            return new CharacterSelectionPrompt();
        }
        connection.setCharacter(dao.getCharacter(characterId));
        return new EnterUniverse();
    }
}
