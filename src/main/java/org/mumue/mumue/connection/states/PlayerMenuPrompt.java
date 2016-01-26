package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
public class PlayerMenuPrompt implements ConnectionState {
    private final ConnectionStateService connectionStateService;
    private final TextMaker textMaker;

    @Inject
    public PlayerMenuPrompt(ConnectionStateService connectionStateService, TextMaker textMaker) {
        this.connectionStateService = connectionStateService;
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
        return connectionStateService.get(PlayerMenuHandler.class);
    }
}
