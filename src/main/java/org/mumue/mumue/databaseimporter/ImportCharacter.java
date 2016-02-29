package org.mumue.mumue.databaseimporter;

import org.mumue.mumue.components.character.GameCharacter;

class ImportCharacter extends GameCharacter {
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
