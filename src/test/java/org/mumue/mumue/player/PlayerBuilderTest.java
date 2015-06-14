package org.mumue.mumue.player;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

public class PlayerBuilderTest {
    private final PlayerBuilder builder = new PlayerBuilder();

    @Test
    public void buildSetsId() {
        long id = RandomUtils.nextLong(100, 200);

        Player player = builder.withId(id).build();

        assertThat(player.getId(), equalTo(id));
    }

    @Test
    public void buildSetsCreated() {
        Instant created = Instant.now().minusSeconds(RandomUtils.nextLong(100, 200));

        Player player = builder.withCreated(created).build();

        assertThat(player.getCreated(), equalTo(created));
    }

    @Test
    public void buildSetsModified() {
        Instant modified = Instant.now().minusSeconds(RandomUtils.nextLong(100, 200));

        Player player = builder.withLastModified(modified).build();

        assertThat(player.getLastModified(), equalTo(modified));
    }

    @Test
    public void buildSetsUsed() {
        Instant lastUsed = Instant.now().minusSeconds(RandomUtils.nextLong(100, 200));

        Player player = builder.withLastUsed(lastUsed).build();

        assertThat(player.getLastUsed(), equalTo(lastUsed));
    }

    @Test
    public void buildSetsUseCount() {
        long useCount = RandomUtils.nextLong(100, 200);

        Player player = builder.withUseCount(useCount).build();

        assertThat(player.getUseCount(), equalTo(useCount));
    }

    @Test
    public void buildSetsLoginId() {
        String loginId = RandomStringUtils.randomAlphabetic(17);

        Player player = builder.withLoginId(loginId).build();

        assertThat(player.getLoginId(), equalTo(loginId));
    }

    @Test
    public void buildSetsLocale() {
        String locale = RandomStringUtils.randomAlphabetic(17);

        Player player = builder.withLocale(locale).build();

        assertThat(player.getLocale(), equalTo(locale));
    }

    @Test
    public void buildSetsAdministrator() {
        Player player = builder.withAdministrator().build();

        assertTrue(player.isAdministrator());
    }
}
