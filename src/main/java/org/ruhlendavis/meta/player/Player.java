package org.ruhlendavis.meta.player;

import java.util.ArrayList;
import java.util.List;

import org.ruhlendavis.meta.componentsold.GameCharacter;

public class Player {
    String name = "";
    String password = "";
    List<GameCharacter> characters = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GameCharacter> getCharacters() {
        return characters;
    }
}
