package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class ImportPlayerTest {
    private static final Random RANDOM = new Random();

    @Test
    public void setLoginId() {
        ImportCharacter character = new ImportCharacter();
        character.setName(RandomStringUtils.insecure().nextAlphabetic(16));
        ImportPlayer player = new ImportPlayer(character);

        assertThat(player.getLoginId(), equalTo(character.getName()));
    }

    @Test
    public void setCreatedOn() {
        ImportCharacter character = new ImportCharacter();
        character.setCreated(Instant.now().minus(10, ChronoUnit.DAYS));
        ImportPlayer player = new ImportPlayer(character);

        assertThat(player.getCreated(), equalTo(character.getCreated()));
    }

    @Test
    public void setLastModifiedOn() {
        ImportCharacter character = new ImportCharacter();
        character.setLastModified(Instant.now().minus(10, ChronoUnit.DAYS));
        ImportPlayer player = new ImportPlayer(character);

        assertThat(player.getLastModified(), equalTo(character.getLastModified()));
    }

    @Test
    public void setLastUsedOn() {
        ImportCharacter character = new ImportCharacter();
        character.setLastUsed(Instant.now().minus(10, ChronoUnit.DAYS));
        ImportPlayer player = new ImportPlayer(character);

        assertThat(player.getLastUsed(), equalTo(character.getLastUsed()));
    }

    @Test
    public void setUseCount() {
        ImportCharacter character = new ImportCharacter();
        character.setUseCount(RANDOM.nextInt(1000) + 1);

        ImportPlayer player = new ImportPlayer(character);

        assertThat(player.getUseCount(), equalTo(character.getUseCount()));
    }

    @Test
    public void setPassword() {
        ImportCharacter character = new ImportCharacter();
        character.setPassword(RandomStringUtils.insecure().nextAlphanumeric(25));

        ImportPlayer player = new ImportPlayer(character);

        assertThat(player.getPassword(), equalTo(character.getPassword()));
    }
}
