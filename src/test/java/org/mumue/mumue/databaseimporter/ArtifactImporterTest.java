package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.components.Artifact;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseBuilder;

public class ArtifactImporterTest {
    private static final Random RANDOM = new Random();
    private final ArtifactImporter artifactImporter = new ArtifactImporter();
    private final DatabaseItemLinesBuilder databaseItemLinesBuilder = new DatabaseItemLinesBuilder();

    @Test
    public void neverReturnNull() {
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.THING).build();

        Artifact artifact = artifactImporter.importFrom(lines, new Universe());

        assertThat(artifact, notNullValue());
    }

    @Test
    public void setReferenceId() {
        long id = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.THING).withId(id).build();

        Artifact artifact = artifactImporter.importFrom(lines, new Universe());

        assertThat(artifact.getId(), equalTo(id));
    }

    @Test
    public void setName() {
        String name = RandomStringUtils.randomAlphabetic(16);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.THING).withName(name).build();

        Artifact artifact = artifactImporter.importFrom(lines, new Universe());

        assertThat(artifact.getName(), equalTo(name));
    }

    @Test
    public void setLocationId() {
        long locationId = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.THING).withLocationId(locationId).build();

        Artifact artifact = artifactImporter.importFrom(lines, new Universe());

        assertThat(artifact.getLocationId(), equalTo(locationId));
    }

    @Test
    public void setUniverseId() {
        long universeId = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.THING).build();

        Artifact artifact = artifactImporter.importFrom(lines, new UniverseBuilder().withId(universeId).build());

        assertThat(artifact.getUniverseId(), equalTo(universeId));
    }

}