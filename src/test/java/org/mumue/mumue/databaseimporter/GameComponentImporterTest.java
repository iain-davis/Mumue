package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.components.Artifact;
import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.GameComponent;
import org.mumue.mumue.components.Link;
import org.mumue.mumue.components.LocatableComponent;
import org.mumue.mumue.components.MufProgram;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.space.Space;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseBuilder;
import org.mumue.mumue.importer.GlobalConstants;

public class GameComponentImporterTest {
    private static final Random RANDOM = new Random();
    private static final int ITEM_FLAGS_INDEX = 5;
    private final DatabaseItemLinesBuilder databaseItemLinesBuilder = new DatabaseItemLinesBuilder();
    private final GameComponentImporter gameComponentImporter = new GameComponentImporter();

    @Test
    public void createCorrectType() {
        List<String> lines = databaseItemLinesBuilder.withRandomType().build();

        FuzzballDatabaseItemType type = FuzzballDatabaseItemType.fromLine(lines.get(ITEM_FLAGS_INDEX));

        GameComponent component = gameComponentImporter.importFrom(lines, new Universe());

        assertThat(component, instanceOf(typeClass(type)));
    }

    @Test
    public void importReferenceId() {
        long id = RANDOM.nextInt(10000);
        List<String> lines = databaseItemLinesBuilder.withRandomType().withId(id).build();

        GameComponent component = gameComponentImporter.importFrom(lines, new Universe());

        assertThat(component, notNullValue());
        assertThat(component.getId(), equalTo(id));
    }

    @Test
    public void importCreatedTimestamp() {
        Instant createdOn = Instant.now().minus(10, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);

        List<String> lines = databaseItemLinesBuilder.createdOn(createdOn).build();

        GameComponent component = gameComponentImporter.importFrom(lines, new Universe());

        assertThat(component, notNullValue());
        assertThat(component.getCreated(), equalTo(createdOn));
    }

    @Test
    public void importLastUsedTimestamp() {
        Instant lastUsed = Instant.now().minus(10, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);

        List<String> lines = databaseItemLinesBuilder.lastUsedOn(lastUsed).build();

        GameComponent component = gameComponentImporter.importFrom(lines, new Universe());

        assertThat(component, notNullValue());
        assertThat(component.getLastUsed(), equalTo(lastUsed));
    }

    @Test
    public void importUseCount() {
        long useCount = RANDOM.nextInt(100000);

        List<String> lines = databaseItemLinesBuilder.withUseCount(useCount).build();

        GameComponent component = gameComponentImporter.importFrom(lines, new Universe());

        assertThat(component, notNullValue());
        assertThat(component.getUseCount(), equalTo(useCount));
    }

    @Test
    public void importLastModifiedTimestamp() {
        Instant lastModifiedOn = Instant.now().minus(10, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);

        List<String> lines = databaseItemLinesBuilder.lastModifiedOn(lastModifiedOn).build();

        GameComponent component = gameComponentImporter.importFrom(lines, new Universe());

        assertThat(component, notNullValue());
        assertThat(component.getLastModified(), equalTo(lastModifiedOn));
    }

    @Test
    public void importName() {
        String name = RandomStringUtils.randomAlphabetic(16);

        List<String> lines = databaseItemLinesBuilder.withName(name).build();

        GameComponent component = gameComponentImporter.importFrom(lines, new Universe());

        assertThat(component, notNullValue());
        assertThat(component.getName(), equalTo(name));
    }

    @Test
    public void importDescription() {
        String description = RandomStringUtils.randomAlphabetic(16);

        List<String> lines = databaseItemLinesBuilder.withDescription(description).build();

        GameComponent component = gameComponentImporter.importFrom(lines, new Universe());

        assertThat(component, notNullValue());
        assertThat(component.getDescription(), equalTo(description));
    }

    @Test
    public void setUniverseId() {
        Universe universe = new UniverseBuilder().withId(RANDOM.nextInt(1000)).build();

        List<String> lines = databaseItemLinesBuilder.build();

        LocatableComponent component = gameComponentImporter.importFrom(lines, universe);

        assertThat(component, notNullValue());
        assertThat(component.getUniverseId(), equalTo(universe.getId()));
    }

    @Test
    public void importLocationId() {
        long locationId = RANDOM.nextInt(100);
        List<String> lines = databaseItemLinesBuilder.withLocationId(locationId).build();

        LocatableComponent component = gameComponentImporter.importFrom(lines, new Universe());
        assertThat(component.getLocationId(), equalTo(locationId));
    }

    @Test
    public void doNotProcessGarbage() {
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.GARBAGE).build();

        LocatableComponent component = gameComponentImporter.importFrom(lines, new Universe());

        assertThat(component, notNullValue());
        assertThat(component.getId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
        assertThat(component.getName(), equalTo(""));
        assertThat(component.getDescription(), equalTo(""));
        assertThat(component.getUniverseId(), equalTo(GlobalConstants.REFERENCE_UNKNOWN));
    }

    private Class<? extends Component> typeClass(FuzzballDatabaseItemType type) {
        switch (type) {
            case CHARACTER:
                return GameCharacter.class;
            case EXIT:
                return Link.class;
            case GARBAGE:
                return Garbage.class;
            case PROGRAM:
                return MufProgram.class;
            case ROOM:
                return Space.class;
            case THING:
                return Artifact.class;
            default:
                return null;
        }
    }
}