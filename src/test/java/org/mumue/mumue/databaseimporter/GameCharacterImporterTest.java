package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.databaseimporter.testapi.DatabaseItemLinesBuilder;

public class GameCharacterImporterTest {
    private static final Random RANDOM = new Random();
    private final DatabaseItemLinesBuilder databaseItemLinesBuilder = new DatabaseItemLinesBuilder();
    private final GameCharacterImporter importer = new GameCharacterImporter();

    @Test
    public void importFromSetsHomeId() {
        long homeId = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.CHARACTER).withHomeId(homeId).build();

        ImportCharacter character = (ImportCharacter) importer.importFrom(lines);

        assertThat(character, notNullValue());
        assertThat(character.getHomeLocationId(), equalTo(homeId));
    }

    @Test
    public void importFromSetsPassword() {
        String password = RandomStringUtils.insecure().nextAlphabetic(13);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.CHARACTER)
                .withPassword(password).build();

        ImportCharacter character = (ImportCharacter) importer.importFrom(lines);

        assertThat(character, notNullValue());
        assertThat(character.getPassword(), equalTo(password));
    }
}
