package org.ruhlendavis.meta;

import java.util.ArrayList;
import java.util.List;

public class Player {
    String name = "";
    String password = "";
    List<Character> characters = new ArrayList<>();

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

    public List<Character> getCharacters() {
        return characters;
    }
}
