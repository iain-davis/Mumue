package org.ruhlendavis.mumue.connection.stages.playing;

import java.util.HashMap;
import java.util.Map;

import org.ruhlendavis.mumue.components.Universe;
import org.ruhlendavis.mumue.components.UniverseDao;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

public class EnterUniverse implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();
    private UniverseDao dao = new UniverseDao();
    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        Universe universe = dao.getUniverse(connection.getCharacter().getUniverseId());
        Map<String, String> variables = new HashMap<>();
        variables.put("universe name", universe.getName());
        String text = textMaker.getText(TextName.EnterUniverse, connection.getPlayer().getLocale(), configuration.getServerLocale(), variables);
        connection.getOutputQueue().push(text);
        return new EnterSpace();
    }
}
