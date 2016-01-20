package org.mumue.mumue.connection;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.configuration.PortConfiguration;
import org.mumue.mumue.player.Player;
import org.mumue.mumue.text.TextQueue;

public class Connection {
    private TextQueue inputQueue = new TextQueue();
    private TextQueue outputQueue = new TextQueue();
    private GameCharacter character = new GameCharacter();
    private Player player;
    private Map<String, Long> menuOptionIds = new HashMap<>();
    private ApplicationConfiguration configuration;
    private PortConfiguration portConfiguration = new PortConfiguration();

    @Inject
    public Connection(ApplicationConfiguration configuration) {
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
        if (getPlayer() == null || StringUtils.isBlank(getPlayer().getLocale())) {
            return configuration.getServerLocale();
        }
        return getPlayer().getLocale();
    }

    public PortConfiguration getPortConfiguration() {
        return portConfiguration;
    }

    public void setPortConfiguration(PortConfiguration portConfiguration) {
        this.portConfiguration = portConfiguration;
    }
}
