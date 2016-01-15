package org.mumue.mumue.connection.stages.playing;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseDao;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class EnterUniverse implements ConnectionStage {
    private final Injector injector;
    private final TextMaker textMaker;
    private final UniverseDao dao;

    @Inject
    public EnterUniverse(Injector injector, TextMaker textMaker, UniverseDao dao) {
        this.injector = injector;
        this.textMaker = textMaker;
        this.dao = dao;
    }

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        Universe universe = dao.getUniverse(connection.getCharacter().getUniverseId());
        Map<String, String> variables = new HashMap<>();
        variables.put("universe name", universe.getName());
        String text = textMaker.getText(TextName.EnterUniverse, connection.getLocale(), variables);
        connection.getOutputQueue().push(text);
        return injector.getInstance(EnterSpace.class);
    }
}
