package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseBuilder;

public class CharacterImporterTest {
    private static final Random RANDOM = new Random();
    private final CharacterImporter characterImporter = new CharacterImporter();
    private final DatabaseItemLinesBuilder databaseItemLinesBuilder = new DatabaseItemLinesBuilder();

    @Test
    public void neverReturnNull() {
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.CHARACTER).build();

        GameCharacter character = characterImporter.importFrom(lines, new Universe());

        assertThat(character, notNullValue());
    }

    @Test
    public void setReferenceId() {
        long id = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.CHARACTER).withId(id).build();

        GameCharacter character = characterImporter.importFrom(lines, new Universe());

        assertThat(character.getId(), equalTo(id));
    }

    @Test
    public void setName() {
        String name = RandomStringUtils.randomAlphabetic(16);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.CHARACTER).withName(name).build();

        GameCharacter character = characterImporter.importFrom(lines, new Universe());

        assertThat(character.getName(), equalTo(name));
    }

    @Test
    public void setLocationId() {
        long locationId = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.CHARACTER).withLocationId(locationId).build();

        GameCharacter character = characterImporter.importFrom(lines, new Universe());

        assertThat(character.getLocationId(), equalTo(locationId));
    }

    @Test
    public void setUniverseId() {
        long universeId = RANDOM.nextInt(10000);
        Universe universe = new UniverseBuilder().withId(universeId).build();
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.CHARACTER).build();

        GameCharacter character = characterImporter.importFrom(lines, universe);

        assertThat(character.getUniverseId(), equalTo(universeId));
    }
}