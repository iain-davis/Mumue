package org.ruhlendavis.mumue.connection.stages.mainmenu;

import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

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
