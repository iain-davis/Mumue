package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.components.Link;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseBuilder;

public class LinkImporterTest {
    private static final Random RANDOM = new Random();
    private final LinkImporter linkImporter = new LinkImporter();
    private final DatabaseItemLinesBuilder databaseItemLinesBuilder = new DatabaseItemLinesBuilder();

    @Test
    public void neverReturnNull() {
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.EXIT).build();

        Link link = linkImporter.importFrom(lines, new Universe());

        assertThat(link, notNullValue());
    }

    @Test
    public void setReferenceId() {
        long id = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.EXIT).withId(id).build();

        Link link = linkImporter.importFrom(lines, new Universe());

        assertThat(link.getId(), equalTo(id));
    }

    @Test
    public void setName() {
        String name = RandomStringUtils.randomAlphabetic(16);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.EXIT).withName(name).build();

        Link link = linkImporter.importFrom(lines, new Universe());

        assertThat(link.getName(), equalTo(name));
    }

    @Test
    public void setLocationId() {
        long locationId = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.EXIT).withLocationId(locationId).build();

        Link link = linkImporter.importFrom(lines, new Universe());

        assertThat(link.getLocationId(), equalTo(locationId));
    }

    @Test
    public void setUniverseId() {
        long universeId = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.EXIT).build();

        Link link = linkImporter.importFrom(lines, new UniverseBuilder().withId(universeId).build());

        assertThat(link.getUniverseId(), equalTo(universeId));
    }
}