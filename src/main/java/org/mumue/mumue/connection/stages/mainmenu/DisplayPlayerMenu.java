package org.mumue.mumue.connection.stages.mainmenu;

import org.mumue.mumue.player.Player;
import org.mumue.mumue.text.TextName;
import org.mumue.mumue.configuration.Configuration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.connection.stages.ConnectionStage;
import org.mumue.mumue.text.TextMaker;

public class DisplayPlayerMenu implements ConnectionStage {
    private TextMaker textMaker = new TextMaker();

    @Override
    public ConnectionStage execute(Connection connection, Configuration configuration) {
        Player player = connection.getPlayer();
        String menu = textMaker.getText(TextName.PlayerMainMenu, connection.getLocale());
        if (player.isAdministrator()) {
            menu = textMaker.getText(TextName.AdministratorMainMenu, connection.getLocale()) + menu;
        }
        connection.getOutputQueue().push(menu);
        return new WaitForPlayerMenuChoice();
    }
}
