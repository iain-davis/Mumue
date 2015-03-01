package org.ruhlendavis.mumue.player;

import java.time.Instant;

import org.ruhlendavis.mumue.importer.GlobalConstants;

public class PlayerBuilder {
    private long id = GlobalConstants.REFERENCE_UNKNOWN;
    private Instant created = Instant.now();
    private Instant lastModified = Instant.now();
    private Instant lastUsed = Instant.now();
    private long useCount = 0;
    private String loginId = "";
    private String locale = "";
    private boolean administrator = false;

    public Player build() {
        Player player = new Player();
        player.setId(id);
        player.setCreated(created);
        player.setLastModified(lastModified);
        player.setLastUsed(lastUsed);
        player.setUseCount(useCount);
        player.setLoginId(loginId);
        player.setLocale(locale);
        player.setAdministrator(administrator);
        return player;
    }

    public PlayerBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public PlayerBuilder withCreated(Instant created) {
        this.created = created;
        return this;
    }

    public PlayerBuilder withLastModified(Instant lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    public PlayerBuilder withLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
        return this;
    }

    public PlayerBuilder withUseCount(long useCount) {
        this.useCount = useCount;
        return this;
    }

    public PlayerBuilder withLoginId(String loginId) {
        this.loginId = loginId;
        return this;
    }

    public PlayerBuilder withLocale(String locale) {
        this.locale = locale;
        return this;
    }

    public PlayerBuilder withAdministrator() {
        this.administrator = true;
        return this;
    }
}
