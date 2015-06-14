package org.mumue.mumue.connection.stages.playing;

import java.util.HashMap;
import java.util.Map;

import org.mumue.mumue.text.TextName;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseDao;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;

public class EnterUniverse implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();
    private UniverseDao dao = new UniverseDao();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        Universe universe = dao.getUniverse(connection.getCharacter().getUniverseId());
        Map<String, String> variables = new HashMap<>();
        variables.put("universe name", universe.getName());
        String text = textMaker.getText(TextName.EnterUniverse, connection.getLocale(), variables);
        connection.getOutputQueue().push(text);
        return new EnterSpace();
    }
}
