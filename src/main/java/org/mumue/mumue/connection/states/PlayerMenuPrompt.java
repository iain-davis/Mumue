package org.mumue.mumue.connection.states;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
class PlayerMenuPrompt implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final TextMaker textMaker;

    @Inject
    public PlayerMenuPrompt(ConnectionStateProvider connectionStateProvider, TextMaker textMaker) {
        this.connectionStateProvider = connectionStateProvider;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        Player player = connection.getPlayer();
        String menu = textMaker.getText(TextName.PlayerMainMenu, connection.getLocale());
        if (player.isAdministrator()) {
            menu = textMaker.getText(TextName.AdministrationMenuOption, connection.getLocale()) + menu;
        }
        connection.getOutputQueue().push(menu);
        return connectionStateProvider.get(PlayerMenuHandler.class);
    }
}
