package org.mumue.mumue.databaseimporter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.mumue.mumue.components.Artifact;
import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.GameComponent;
import org.mumue.mumue.components.LocatableComponent;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.space.Space;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseBuilder;
import org.mumue.mumue.importer.GlobalConstants;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

public class ComponentsImporterTest {
    private final DatabaseItemLinesBuilder databaseItemLinesBuilder = new DatabaseItemLinesBuilder();
    private final ComponentsImporter importer = new ComponentsImporter(new GameComponentImporter());
    private final Universe universe = new UniverseBuilder().withId(RandomUtils.insecure().randomLong(1, 100)).build();

    @Test
    public void neverReturnNull() {
        List<Component> components = importer.importFrom(databaseItemLinesBuilder.build(), universe);

        assertThat(components, notNullValue());
    }

    @Test
    public void importOneCharacterComponent() {
        long homeId = RandomUtils.insecure().randomLong(1, 100);
        String password = RandomStringUtils.insecure().nextAlphabetic(13);
        List<String> lines = databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.CHARACTER)
                .withPassword(password)
                .withHomeId(homeId)
                .build();
        List<Component> components = importer.importFrom(lines, universe);

        assertThat(components, notNullValue());
        assertThat(components, hasItem(isA(GameCharacter.class)));
        ImportCharacter character = (ImportCharacter) getComponentMatching(ImportCharacter.class, components);
        assertThat(character.getHomeLocationId(), equalTo(homeId));
        assertThat(character.getPassword(), equalTo(password));
    }

    @Test
    public void createPlayerForCharacter() {
        String name = RandomStringUtils.insecure().nextAlphabetic(17);
        String password = RandomStringUtils.insecure().nextAlphanumeric(25);
        Instant createdOn = Instant.now().minus(10, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
        Instant lastModified = Instant.now().minus(9, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
        Instant lastUsed = Instant.now().minus(8, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
        long useCount = RandomUtils.insecure().randomInt(1, 10000);
        List<String> lines = databaseItemLinesBuilder
                .createdOn(createdOn)
                .withPassword(password)
                .lastModifiedOn(lastModified)
                .lastUsedOn(lastUsed)
                .withUseCount(useCount)
                .withType(FuzzballDatabaseItemType.CHARACTER)
                .withName(name)
                .build();
        List<Component> components = importer.importFrom(lines, universe);

        assertThat(components, notNullValue());
        assertThat(components, hasItem(isA(ImportPlayer.class)));
        ImportPlayer player = (ImportPlayer) getComponentMatching(ImportPlayer.class, components);

        assertThat(player.getLoginId(), equalTo(name));
        assertThat(player.getPassword(), equalTo(password));
        assertThat(player.getCreated(), equalTo(createdOn));
        assertThat(player.getLastModified(), equalTo(lastModified));
        assertThat(player.getLastUsed(), equalTo(lastUsed));
        assertThat(player.getUseCount(), equalTo(useCount));
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
        lines.addAll(databaseItemLinesBuilder.withType(FuzzballDatabaseItemType.THING).build());

        List<Component> components = importer.importFrom(lines, universe);

        assertThat(components, notNullValue());
        assertThat(components.size(), equalTo(2));
        assertThat(components, hasItem(isA(Space.class)));
        assertThat(components, hasItem(isA(Artifact.class)));
    }

    @Test
    public void importComponentReferenceId() {
        List<String> lines = new ArrayList<>();
        lines.addAll(databaseItemLinesBuilder.withId(RandomUtils.insecure().randomInt(1, 100)).build());
        lines.addAll(databaseItemLinesBuilder.withId(RandomUtils.insecure().randomInt(101, 200)).build());

        List<Component> components = importer.importFrom(lines, universe);

        for (Component component : components) {
            assertThat(component.getId(), not(equalTo(GlobalConstants.REFERENCE_UNKNOWN)));
        }
    }

    @Test
    public void importComponentName() {
        String name = RandomStringUtils.insecure().nextAlphabetic(25);
        List<String> lines = new ArrayList<>(databaseItemLinesBuilder.withName(name).build());

        List<Component> components = importer.importFrom(lines, universe);

        GameComponent component = (GameComponent) components.get(0);
        assertThat(component.getName(), equalTo(name));
    }

    @Test
    public void importComponentLocationId() {
        long locationId = RandomUtils.insecure().randomInt(1, 100);
        List<String> lines = new ArrayList<>(databaseItemLinesBuilder.withLocationId(locationId).build());

        List<Component> components = importer.importFrom(lines, universe);

        LocatableComponent component = (LocatableComponent) components.get(0);
        assertThat(component.getLocationId(), equalTo(locationId));
    }

    @Test
    public void importComponentUniverseId() {
        long universeId = RandomUtils.insecure().randomInt(1, 100);
        Universe universe = new UniverseBuilder().withId(universeId).build();
        List<String> lines = new ArrayList<>(databaseItemLinesBuilder.withRandomId().build());

        List<Component> components = importer.importFrom(lines, universe);

        LocatableComponent component = (LocatableComponent) components.get(0);
        assertThat(component.getUniverseId(), equalTo(universeId));
    }

    private Component getComponentMatching(Class<? extends Component> componentType, List<Component> components) {
        //noinspection OptionalGetWithoutIsPresent
        return components.stream().filter(componentType::isInstance).findFirst().get();
    }
}
