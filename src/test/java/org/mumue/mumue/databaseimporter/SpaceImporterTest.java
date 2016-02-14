package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.components.space.Space;

public class SpaceImporterTest {
    private static final Random RANDOM = new Random();
    private final SpaceImporter spaceImporter = new SpaceImporter();
    private final DatabaseItemLinesBuilder databaseItemLinesBuilder = new DatabaseItemLinesBuilder();

    @Test
    public void neverReturnNull() {
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.ROOM).build();

        Space space = spaceImporter.importFrom(lines);

        assertThat(space, notNullValue());
    }

    @Test
    public void setReferenceId() {
        long id = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.ROOM).withId(id).build();

        Space space = spaceImporter.importFrom(lines);

        assertThat(space.getId(), equalTo(id));
    }

    @Test
    public void setName() {
        String name = RandomStringUtils.randomAlphabetic(16);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.ROOM).withName(name).build();

        Space space = spaceImporter.importFrom(lines);

        assertThat(space.getName(), equalTo(name));
    }

    @Test
    public void setLocation() {
        long locationId = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.ROOM).withLocationId(locationId).build();

        Space space = spaceImporter.importFrom(lines);

        assertThat(space.getLocationId(), equalTo(locationId));
    }
}