package org.mumue.mumue.databaseimporter;

import org.mumue.mumue.player.Player;

class ImportPlayer extends Player {
    private String password;

    public ImportPlayer(ImportCharacter character) {
        setLoginId(character.getName());
        setCreated(character.getCreated());
        setLastModified(character.getLastModified());
        setLastUsed(character.getLastUsed());
        setUseCount(character.getUseCount());
        setPassword(character.getPassword());
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
