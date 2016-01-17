package org.mumue.mumue.connection.stages.mainmenu;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseDao;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class UniverseSelectionPrompt implements ConnectionStage {
    private final Injector injector;
    private final UniverseDao universeDao;
    private final TextMaker textMaker;

    @Inject
    public UniverseSelectionPrompt(Injector injector, UniverseDao universeDao, TextMaker textMaker) {
        this.injector = injector;
        this.universeDao = universeDao;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionStage execute(Connection connection, ApplicationConfiguration configuration) {
        StringBuilder builder = new StringBuilder(GlobalConstants.TCP_LINE_SEPARATOR);
        for (Universe universe : universeDao.getUniverses()) {
            builder.append(universe.getId()).append(") ").append(universe.getName()).append(GlobalConstants.TCP_LINE_SEPARATOR);
        }
        connection.getOutputQueue().push(builder.toString());

        String text = textMaker.getText(TextName.UniverseSelectionPrompt, connection.getLocale());
        connection.getOutputQueue().push(text);
        return injector.getInstance(WaitForUniverseSelection.class);
    }
}
