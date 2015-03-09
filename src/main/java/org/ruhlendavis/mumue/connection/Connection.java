package org.ruhlendavis.mumue.connection;

import java.util.HashMap;
import java.util.Map;

import org.ruhlendavis.mumue.components.character.GameCharacter;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.text.TextQueue;

public class Connection {
    private TextQueue inputQueue = new TextQueue();
    private TextQueue outputQueue = new TextQueue();
    private GameCharacter character = new GameCharacter();
    private Player player;
    private Map<String, Long> menuOptionIds = new HashMap<>();
    private Configuration configuration;

    public Connection(Configuration configuration) {
        this.configuration = configuration;
    }

    public TextQueue getInputQueue() {
        return inputQueue;
    }

    public TextQueue getOutputQueue() {
        return outputQueue;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Connection withPlayer(Player player) {
        setPlayer(player);
        return this;
    }

    public GameCharacter getCharacter() {
        return character;
    }

    public void setCharacter(GameCharacter character) {
        this.character = character;
    }

    public Connection withCharacter(GameCharacter character) {
        setCharacter(character);
        return this;
    }

    public Map<String, Long> getMenuOptionIds() {
        return menuOptionIds;
    }

    public String getLocale() {
        if (getPlayer() == null) {
            return configuration.getServerLocale();
        }
        return getPlayer().getLocale();
    }
}
