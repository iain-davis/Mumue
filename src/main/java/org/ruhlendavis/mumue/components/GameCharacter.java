package org.ruhlendavis.mumue.components;

public class GameCharacter extends LocatableComponent {
    String playerId = "";

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public GameCharacter withPlayerId(String playerId) {
        setPlayerId(playerId);
        return this;
    }
}
