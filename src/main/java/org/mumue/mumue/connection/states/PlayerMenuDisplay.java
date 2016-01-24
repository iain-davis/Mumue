package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.inject.Injector;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class PlayerMenuDisplay implements ConnectionState {
    private final WaitForPlayerMenuChoice waitForPlayerMenuChoice;
    private final TextMaker textMaker;

    @Inject
    @Singleton
    public PlayerMenuDisplay(WaitForPlayerMenuChoice waitForPlayerMenuChoice, TextMaker textMaker) {
        this.waitForPlayerMenuChoice = waitForPlayerMenuChoice;
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
        return waitForPlayerMenuChoice;
    }
}
