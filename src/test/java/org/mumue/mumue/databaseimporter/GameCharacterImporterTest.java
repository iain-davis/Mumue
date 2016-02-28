package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.mumue.mumue.components.character.GameCharacter;

public class GameCharacterImporterTest {
    private static final Random RANDOM = new Random();
    private final DatabaseItemLinesBuilder databaseItemLinesBuilder = new DatabaseItemLinesBuilder();
    private final GameCharacterImporter importer = new GameCharacterImporter();

    @Test
    public void importFromSetsHomeId() {
        long homeId = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.CHARACTER).withHomeId(homeId).build();

        GameCharacter character = importer.importFrom(lines);

        assertThat(character, notNullValue());
        assertThat(character.getHomeLocationId(), equalTo(homeId));
    }
}