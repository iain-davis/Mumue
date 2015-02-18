package org.ruhlendavis.meta.importer.stages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import org.ruhlendavis.meta.componentsold.GameCharacter;
import org.ruhlendavis.meta.componentsold.Space;
import org.ruhlendavis.meta.importer.ImportBucket;
import org.ruhlendavis.meta.importer.ImporterStageTestHelper;
import org.ruhlendavis.meta.player.Player;

public class PlayerGenerationStageTest extends ImporterStageTestHelper {
    PlayerGenerationStage stage = new PlayerGenerationStage();

    @Test
    public void runWithOneCharacterCreatesOnePlayer() {
        ImportBucket bucket = new ImportBucket();

        String name = RandomStringUtils.randomAlphabetic(13);
        String password = RandomStringUtils.randomAlphabetic(13);
        mockCharacterData(bucket, name, password, RandomUtils.nextLong(2, 100));

        stage.run(bucket);

        assertEquals(1, bucket.getPlayers().size());
    }

    @Test
    public void runWithCharacterAddsCharacterToCreatedPlayer() {
        ImportBucket bucket = new ImportBucket();

        String name = RandomStringUtils.randomAlphabetic(13);
        String password = RandomStringUtils.randomAlphabetic(13);
        Long id = RandomUtils.nextLong(2, 100);
        mockCharacterData(bucket, name, password, id);

        stage.run(bucket);

//        assertSame(bucket.getComponents().get(id), bucket.getPlayers().get(0).getCharacters().get(0));
    }

    @Test
    public void runWithOneCharacterCreatesOnePlayerWithName() {
        ImportBucket bucket = new ImportBucket();

        String name = RandomStringUtils.randomAlphabetic(13);
        String password = RandomStringUtils.randomAlphabetic(13);
        mockCharacterData(bucket, name, password, RandomUtils.nextLong(2, 100));

        stage.run(bucket);

        assertEquals(name, bucket.getPlayers().get(0).getName());
    }

    @Test
    public void runWithTwoCharactersCreatesTwoPlayers() {
        ImportBucket bucket = new ImportBucket();

        String name1 = RandomStringUtils.randomAlphabetic(13);
        String password1 = RandomStringUtils.randomAlphabetic(13);
        mockCharacterData(bucket, name1, password1, RandomUtils.nextLong(2, 100));

        String name2 = RandomStringUtils.randomAlphabetic(13);
        String password2 = RandomStringUtils.randomAlphabetic(13);
        mockCharacterData(bucket, name2, password2, RandomUtils.nextLong(200, 300));

        stage.run(bucket);

        assertEquals(2, bucket.getPlayers().size());
    }

    @Test
    public void runWithOneCharacterCreatesOnePlayerWithPassword() {
        ImportBucket bucket = new ImportBucket();

        String name = RandomStringUtils.randomAlphabetic(13);
        String password = RandomStringUtils.randomAlphabetic(13);
        mockCharacterData(bucket, name, password, RandomUtils.nextLong(2, 100));

        stage.run(bucket);

//        assertEquals(password, bucket.getPlayers().get(0).getPassword());
    }

    @Test
    public void runOnlyMakesPlayersFromCharacters() {
        ImportBucket bucket = new ImportBucket();

        String name = RandomStringUtils.randomAlphabetic(13);
        String password = RandomStringUtils.randomAlphabetic(13);
        mockCharacterData(bucket, name, password, RandomUtils.nextLong(2, 100));

        Space space = new Space().withId(RandomUtils.nextLong(100,200));
        bucket.getComponentLines().put(space.getReference(), mockComponentLines(space, null));
        bucket.getComponents().put(space.getReference(), space);

        stage.run(bucket);

        assertEquals(1, bucket.getPlayers().size());
    }

    @Test
    public void runWithoutCharactersDoesNotMakePlayers() {
        ImportBucket bucket = new ImportBucket();
        Space space = new Space().withId(RandomUtils.nextLong(100,200));
        bucket.getComponentLines().put(space.getReference(), mockComponentLines(space, null));
        bucket.getComponents().put(space.getReference(), space);

        stage.run(bucket);

        assertEquals(0, bucket.getPlayers().size());
    }

    @Test
    public void runWithoutComponentsDoesNotMakePlayers() {
        ImportBucket bucket = new ImportBucket();
        stage.run(bucket);
        assertEquals(0, bucket.getPlayers().size());
    }

    private void mockCharacterData(ImportBucket bucket, String name, String password, Long id) {
        GameCharacter character = new GameCharacter().withId(id);
        character.setName(name);
        Player player = new Player();
        player.setName(name);
//        player.setPassword(password);
        bucket.getComponentLines().put(character.getReference(), mockComponentLines(character, player));
        bucket.getComponents().put(character.getReference(), character);
    }
}
