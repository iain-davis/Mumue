package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.LocatableComponent;
import org.mumue.mumue.components.GameComponent;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.space.Space;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseBuilder;
import org.mumue.mumue.importer.GlobalConstants;

public class ComponentsImporterTest {
    private static final Random RANDOM = new Random();
    private final DatabaseItemLinesBuilder databaseItemLinesBuilder = new DatabaseItemLinesBuilder();
    private final ComponentsImporter importer = new ComponentsImporter(new GameComponentImporter());
    private final Universe universe = new UniverseBuilder().withId(RANDOM.nextInt(100)).build();

    @Test
    public void neverReturnNull() {
        List<Component> components = importer.importFrom(databaseItemLinesBuilder.build(), universe);

        assertThat(components, notNullValue());
    }

    @Test
    public void importOneCharacterComponent() {
        long homeId = RANDOM.nextInt(1000);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.CHARACTER).withHomeId(homeId).build();
        List<Component> components = importer.importFrom(lines, universe);

        assertThat(components, notNullValue());
        assertThat(components.size(), equalTo(1));
        assertThat(components.get(0), instanceOf(GameCharacter.class));
        assertThat(((GameCharacter) components.get(0)).getHomeLocationId(), equalTo(homeId));
    }

    @Test
    public void importOneSpaceComponent() {
        List<Component> components = importer.importFrom(databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.ROOM).build(), universe);

        assertThat(components, notNullValue());
        assertThat(components.size(), equalTo(1));
        assertThat(components.get(0), instanceOf(Space.class));
    }

    @Test
    public void importTwoComponents() {
        List<String> lines = new ArrayList<>();
        lines.addAll(databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.ROOM).build());
        lines.addAll(databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.CHARACTER).build());

        List<Component> components = importer.importFrom(lines, universe);

        assertThat(components, notNullValue());
        assertThat(components.size(), equalTo(2));
        assertThat(components, hasItem(isA(Space.class)));
        assertThat(components, hasItem(isA(GameCharacter.class)));
    }

    @Test
    public void importComponentReferenceId() {
        List<String> lines = new ArrayList<>();
        lines.addAll(databaseItemLinesBuilder.withId(RANDOM.nextInt(100)).build());
        lines.addAll(databaseItemLinesBuilder.withId(RANDOM.nextInt(100)).build());

        List<Component> components = importer.importFrom(lines, universe);

        for (Component component : components) {
            assertThat(component.getId(), not(equalTo(GlobalConstants.REFERENCE_UNKNOWN)));
        }
    }

    @Test
    public void importComponentName() {
        List<String> lines = new ArrayList<>();
        String name = RandomStringUtils.randomAlphabetic(25);
        lines.addAll(databaseItemLinesBuilder.withName(name).build());

        List<Component> components = importer.importFrom(lines, universe);

        GameComponent component = (GameComponent) components.get(0);
        assertThat(component.getName(), equalTo(name));
    }

    @Test
    public void importComponentLocationId() {
        long locationId = RANDOM.nextInt(100);
        List<String> lines = new ArrayList<>();
        lines.addAll(databaseItemLinesBuilder.withLocationId(locationId).build());

        List<Component> components = importer.importFrom(lines, universe);

        LocatableComponent component = (LocatableComponent) components.get(0);
        assertThat(component.getLocationId(), equalTo(locationId));
    }

    @Test
    public void importComponentUniverseId() {
        long universeId = RANDOM.nextInt(100);
        Universe universe = new UniverseBuilder().withId(universeId).build();
        List<String> lines = new ArrayList<>();
        lines.addAll(databaseItemLinesBuilder.withRandomId().build());

        List<Component> components = importer.importFrom(lines, universe);

        LocatableComponent component = (LocatableComponent) components.get(0);
        assertThat(component.getUniverseId(), equalTo(universeId));
    }
}