package org.mumue.mumue.connection.states.mainmenu;

import javax.inject.Inject;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.states.ConnectionState;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class DisplayPlayerMenu implements ConnectionState {
    private final Injector injector;
    private final TextMaker textMaker;

    @Inject
    public DisplayPlayerMenu(Injector injector, TextMaker textMaker) {
        this.injector = injector;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        Player player = connection.getPlayer();
        String menu = textMaker.getText(TextName.PlayerMainMenu, connection.getLocale());
        if (player.isAdministrator()) {
            menu = textMaker.getText(TextName.AdministratorMainMenu, connection.getLocale()) + menu;
        }
        connection.getOutputQueue().push(menu);
        return injector.getInstance(WaitForPlayerMenuChoice.class);
    }
}
