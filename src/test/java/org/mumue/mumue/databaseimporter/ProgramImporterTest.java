package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.components.Program;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseBuilder;

public class ProgramImporterTest {
    private static final Random RANDOM = new Random();
    private final ProgramImporter programImporter = new ProgramImporter();
    private final DatabaseItemLinesBuilder databaseItemLinesBuilder = new DatabaseItemLinesBuilder();

    @Test
    public void neverReturnNull() {
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.PROGRAM).build();

        Program program = programImporter.importFrom(lines, new Universe());

        assertThat(program, notNullValue());
    }

    @Test
    public void setReferenceId() {
        long id = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.PROGRAM).withId(id).build();

        Program program = programImporter.importFrom(lines, new Universe());

        assertThat(program.getId(), equalTo(id));
    }

    @Test
    public void setName() {
        String name = RandomStringUtils.randomAlphabetic(16);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.PROGRAM).withName(name).build();

        Program program = programImporter.importFrom(lines, new Universe());

        assertThat(program.getName(), equalTo(name));
    }

    @Test
    public void setLocationId() {
        long locationId = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.PROGRAM).withLocationId(locationId).build();

        Program program = programImporter.importFrom(lines, new Universe());

        assertThat(program.getLocationId(), equalTo(locationId));
    }

    @Test
    public void setUniverseId() {
        long universeId = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.PROGRAM).build();

        Program program = programImporter.importFrom(lines, new UniverseBuilder().withId(universeId).build());

        assertThat(program.getUniverseId(), equalTo(universeId));
    }
}