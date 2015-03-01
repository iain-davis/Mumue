package org.ruhlendavis.mumue.importer.stages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import org.ruhlendavis.mumue.componentsold.Artifact;
import org.ruhlendavis.mumue.componentsold.Component;
import org.ruhlendavis.mumue.componentsold.GameCharacter;
import org.ruhlendavis.mumue.componentsold.Link;
import org.ruhlendavis.mumue.componentsold.Program;
import org.ruhlendavis.mumue.componentsold.Space;
import org.ruhlendavis.mumue.componentsold.properties.IntegerProperty;
import org.ruhlendavis.mumue.componentsold.properties.LockProperty;
import org.ruhlendavis.mumue.componentsold.properties.ReferenceProperty;
import org.ruhlendavis.mumue.componentsold.properties.StringProperty;
import org.ruhlendavis.mumue.importer.ImportBucket;

public class ProcessComponentLinesStageTest {
    private ProcessComponentLinesStage stage = new ProcessComponentLinesStage();

    @Test
    public void generateShouldSetName() {
        String name = RandomStringUtils.randomAlphanumeric(13);
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(space, name, "");
        stage.run(bucket);
        assertEquals(name, space.getName());
    }

    @Test
    public void generateShouldSetLocation() {
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        Space location = new Space().withId(RandomUtils.nextLong(100, 200));
        ImportBucket bucket = setupTest(space, location);
        stage.run(bucket);
        assertEquals(location.getId(), space.getLocation().getId());
    }

    @Test
    public void generateShouldSetFirstContents() {
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        Artifact artifact = new Artifact().withId(RandomUtils.nextLong(200, 300));
        ImportBucket bucket = setupTest(space, artifact);
        stage.run(bucket);
        assertEquals(artifact.getId(), bucket.getComponents().get(space.getId()).getContents().get(0).getId());
    }

    @Test
    public void generateShouldSetCreated() {
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        Long created = RandomUtils.nextLong(100000, 200000);
        ImportBucket bucket = setupTest(space, created, 0L, 0L);
        stage.run(bucket);
        assertEquals(Instant.ofEpochSecond(created), space.getCreated());
    }

    @Test
    public void generateShouldSetLastUsed() {
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        Long lastUsed = RandomUtils.nextLong(100000, 200000);
        ImportBucket bucket = setupTest(space, 0L, lastUsed, 0L);
        stage.run(bucket);
        assertEquals(Instant.ofEpochSecond(lastUsed), space.getLastUsed());
    }

    @Test
    public void generateShouldSetUseCount() {
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        Integer useCount = RandomUtils.nextInt(100, 500);
        ImportBucket bucket = setupTest(space, useCount);
        stage.run(bucket);
        assertEquals(useCount, space.getUseCount(), 0);
    }

    @Test
    public void generateShouldSetModified() {
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        Long lastModified = RandomUtils.nextLong(100000, 200000);
        ImportBucket bucket = setupTest(space, 0L, 0L, lastModified);
        stage.run(bucket);
        assertEquals(Instant.ofEpochSecond(lastModified), space.getLastModified());
    }

    @Test
    public void generateShouldSetDescription() {
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        String description = RandomStringUtils.randomAlphabetic(255);
        ImportBucket bucket = setupTest(space, "", description);
        stage.run(bucket);
        assertEquals(description, space.getDescription());
    }

    @Test
    public void generateShouldHandleMinimalProperties() {
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(space, new ArrayList<>());
        stage.run(bucket);
        assertEquals(0, space.getProperties().size());
    }

    @Test
    public void generateShouldAddStringPropertyToProperties() {
        String path = RandomStringUtils.randomAlphabetic(8);
        String value = RandomStringUtils.randomAlphabetic(7);
        List<String> properties = new ArrayList<>();
        properties.add(path + ":10:" + value);
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(space, properties);
        stage.run(bucket);
        StringProperty property = (StringProperty)space.getProperties().getProperty(path);
        assertEquals(value, property.getValue());
    }

    @Test
    public void generateShouldAddStringWithColonPropertyToProperties() {
        String path = RandomStringUtils.randomAlphabetic(8);
        String value = RandomStringUtils.randomAlphabetic(7) + ":" + RandomStringUtils.randomAlphanumeric(13);
        List<String> properties = new ArrayList<>();
        properties.add(path + ":10:" + value);
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(space, properties);
        stage.run(bucket);
        StringProperty property = (StringProperty)space.getProperties().getProperty(path);
        assertEquals(value, property.getValue());
    }

    @Test
    public void generateShouldAddLockPropertyToProperties() {
        String path = RandomStringUtils.randomAlphabetic(8);
        String value = RandomStringUtils.randomAlphabetic(7);
        List<String> properties = new ArrayList<>();
        properties.add(path + ":4:" + value);
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(space, properties);
        stage.run(bucket);
        LockProperty property = (LockProperty)space.getProperties().getProperty(path);
        assertEquals(value, property.getValue());
    }

    @Test
    public void generateShouldAddIntegerPropertyToProperties() {
        String path = RandomStringUtils.randomAlphabetic(8);
        String value = RandomStringUtils.randomNumeric(5);
        List<String> properties = new ArrayList<>();
        properties.add(path + ":3:" + value);
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(space, properties);
        stage.run(bucket);
        IntegerProperty property = (IntegerProperty)space.getProperties().getProperty(path);
        assertEquals(Long.parseLong(value), property.getValue(), 0);
    }

    @Test
    public void generateShouldAddReferencePropertyToProperties() {
        String path = RandomStringUtils.randomAlphabetic(8);
        String value = RandomStringUtils.randomNumeric(5);
        List<String> properties = new ArrayList<>();
        properties.add(path + ":5:" + value);
        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(space, properties);
        stage.run(bucket);
        ReferenceProperty property = (ReferenceProperty)space.getProperties().getProperty(path);
        assertEquals(Long.parseLong(value), property.getValue(), 0);
    }

    @Test
    public void generateShouldAddMultipleStringPropertiesToProperties() {
        String path1 = RandomStringUtils.randomAlphabetic(6);
        String path2 = RandomStringUtils.randomAlphabetic(7);
        String value1 = RandomStringUtils.randomAlphabetic(8);
        String value2 = RandomStringUtils.randomAlphabetic(9);

        List<String> properties = new ArrayList<>();
        properties.add(path1 + ":10:" + value1);
        properties.add(path2 + ":10:" + value2);

        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(space, properties);
        stage.run(bucket);

        StringProperty property = (StringProperty)space.getProperties().getProperty(path1);
        assertEquals(value1, property.getValue());

        property = (StringProperty)space.getProperties().getProperty(path2);
        assertEquals(value2, property.getValue());
    }

    @Test
    public void generateWithArtifactShouldSetHome() {
        Artifact artifact = new Artifact().withId(RandomUtils.nextLong(300, 400));
        Space home = new Space().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(artifact, home, null, null, 0L);
        stage.run(bucket);
        assertEquals(home.getId(), artifact.getHome().getId());
    }

    @Test
    public void generateWithArtifactShouldSetFirstLink() {
        Artifact artifact = new Artifact().withId(RandomUtils.nextLong(300, 400));
        Link link = new Link().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(artifact, null, link, null, 0L);
        stage.run(bucket);
        assertEquals(link.getId(), artifact.getLinks().get(0).getId());
    }

    @Test
    public void generateWithArtifactShouldNotSetFirstLink() {
        Artifact artifact = new Artifact().withId(RandomUtils.nextLong(300, 400));
        ImportBucket bucket = setupTest(artifact, null, null, null, 0L);
        stage.run(bucket);
        assertEquals(0, artifact.getLinks().size());
    }

    @Test
    public void generateWithArtifactShouldSetOwner() {
        Artifact artifact = new Artifact().withId(RandomUtils.nextLong(300, 400));
        GameCharacter owner = new GameCharacter().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(artifact, null, null, owner, 0L);
        stage.run(bucket);
        assertEquals(owner.getId(), artifact.getOwner().getId());
    }

    @Test
    public void generateWithArtifactShouldSetValue() {
        Long value = RandomUtils.nextLong(100, 1000);
        Artifact artifact = new Artifact().withId(RandomUtils.nextLong(300, 400));
        ImportBucket bucket = setupTest(artifact, null, null, null, value);
        stage.run(bucket);
        assertEquals(value, artifact.getValue(), 0);
    }

    @Test
    public void generateWithCharacterShouldSetHome() {
        GameCharacter character = new GameCharacter().withId(RandomUtils.nextLong(300, 400));
        Space home = new Space().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(character, home, null, 0L, "");
        stage.run(bucket);
        assertEquals(home.getId(), character.getHome().getId());
    }

    @Test
    public void generateWithCharacterShouldSetFirstLink() {
        GameCharacter character = new GameCharacter().withId(RandomUtils.nextLong(300, 400));
        Link link = new Link().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(character, null, link, 0L, "");
        stage.run(bucket);
        assertEquals(link.getId(), character.getLinks().get(0).getId());
    }

    @Test
    public void generateWithCharacterShouldNotSetFirstLink() {
        GameCharacter character = new GameCharacter().withId(RandomUtils.nextLong(300, 400));
        ImportBucket bucket = setupTest(character, null, null, 0L, "");
        stage.run(bucket);
        assertEquals(0, character.getLinks().size());
    }
    @Test
    public void generateWithCharacterShouldSetWealth() {
        Long value = RandomUtils.nextLong(100, 1000);
        GameCharacter character = new GameCharacter().withId(RandomUtils.nextLong(300, 400));
        ImportBucket bucket = setupTest(character, null, null, value, "");
        stage.run(bucket);
        assertEquals(value, character.getWealth(), 0);
    }

    @Test
    public void generateWithLinkWithoutDestinations() {
        Link link = new Link().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(link, new ArrayList<>(), null);
        stage.run(bucket);
        assertEquals(0, link.getDestinations().size());
    }

    @Test
    public void generateWithLinkShouldSetFirstDestination() {
        Link link = new Link().withId(RandomUtils.nextLong(2, 100));
        Space destination = new Space().withId(RandomUtils.nextLong(300, 400));
        List<Component> destinations = new ArrayList<>();
        destinations.add(destination);
        ImportBucket bucket = setupTest(link, destinations ,null);
        stage.run(bucket);
        assertEquals(destination.getId(), link.getDestinations().get(0).getId());
    }

    @Test
    public void generateWithLinkShouldSetMultipleDestinations() {
        Link link = new Link().withId(RandomUtils.nextLong(2, 100));
        List<Component> destinations = new ArrayList<>();
        int count = RandomUtils.nextInt(3, 10);
        for (int i = 0; i < count; i++) {
            Space destination = new Space().withId(RandomUtils.nextLong(300, 400));
            destinations.add(destination);
        }
        ImportBucket bucket = setupTest(link, destinations ,null);
        stage.run(bucket);
        int i = 0;
        for (Component destination : destinations) {
            assertEquals(destination.getId(), link.getDestinations().get(i).getId());
            i++;
        }
    }

    @Test
    public void generateWithLinkShouldSetOwner() {
        Link link = new Link().withId(RandomUtils.nextLong(300, 400));
        GameCharacter owner = new GameCharacter().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(link, new ArrayList<>(), owner);
        stage.run(bucket);
        assertEquals(owner.getId(), link.getOwner().getId());
    }

    @Test
    public void generateWithProgramShouldSetOwner() {
        Program program = new Program().withId(RandomUtils.nextLong(300, 400));
        GameCharacter owner = new GameCharacter().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(program, owner);
        stage.run(bucket);
        assertEquals(owner.getId(), program.getOwner().getId());
    }

    @Test
    public void generateWithProgramWithHashOwnerReferenceShouldSetOwner() {
        Program program = new Program().withId(RandomUtils.nextLong(300, 400));
        GameCharacter owner = new GameCharacter().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(program, owner, true, false);
        stage.run(bucket);
        assertEquals(owner.getId(), program.getOwner().getId());
    }

    @Test
    public void generateWithProgramWithBlankOwnerShouldSetOwner() {
        Program program = new Program().withId(RandomUtils.nextLong(300, 400));
        ImportBucket bucket = setupTest(program, null, false, true);
        stage.run(bucket);
        assertEquals(1L, program.getOwner().getId(), 0);
    }

    @Test
    public void generateWithSpaceShouldSetDropTo() {
        Space space = new Space().withId(RandomUtils.nextLong(300, 400));
        Space dropTo = new Space().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(space, dropTo, null, null);
        stage.run(bucket);
        assertEquals(dropTo.getId(), space.getDropTo().getId());
    }

    @Test
    public void generateWithSpaceShouldNotSetDropTo() {
        Space space = new Space().withId(RandomUtils.nextLong(300, 400));
        Space dropTo = new Space().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(space, null, null, (GameCharacter)null);
        stage.run(bucket);
        assertNull(space.getDropTo());
    }

    @Test
    public void generateWithSpaceShouldSetFirstLink() {
        Space space = new Space().withId(RandomUtils.nextLong(300, 400));
        Link link = new Link().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(space, null, link, null);
        stage.run(bucket);
        assertEquals(link.getId(), space.getLinks().get(0).getId());
    }

    @Test
    public void generateWithSpaceShouldNotSetFirstLink() {
        Space space = new Space().withId(RandomUtils.nextLong(300, 400));
        ImportBucket bucket = setupTest(space, null, (Link)null, null);
        stage.run(bucket);
        assertEquals(0, space.getLinks().size(), 0);
    }

    @Test
    public void generateWithSpaceShouldSetOwner() {
        Space space = new Space().withId(RandomUtils.nextLong(300, 400));
        GameCharacter owner = new GameCharacter().withId(RandomUtils.nextLong(2, 100));
        ImportBucket bucket = setupTest(space, null, null, owner);
        stage.run(bucket);
        assertEquals(owner.getId(), space.getOwner().getId());
    }

    private ImportBucket setupTest(Artifact artifact, Space home, Link firstLink, GameCharacter owner, Long value) {
        ImportBucket bucket = setupTest(artifact, RandomStringUtils.randomAlphabetic(13), "1 0",
                                        new Space().withId(RandomUtils.nextLong(100, 200)),
                                        new Artifact().withId(RandomUtils.nextLong(200, 300)),
                                        "0 0", 0L, 0L, 0, 0L, new ArrayList<>());
        if (home == null) {
            home = new Space().withId(RandomUtils.nextLong(600, 700));
        }
        if (owner == null) {
            owner = new GameCharacter().withId(RandomUtils.nextLong(500, 600));
        }
        List<String> lines = bucket.getComponentLines().get(artifact.getId());
        setupTargetComponent(bucket, lines, home);
        setupTargetComponent(bucket, lines, firstLink);
        setupTargetComponent(bucket, lines, owner);
        lines.add(value.toString());
        return bucket;
    }

    private ImportBucket setupTest(GameCharacter character, Space home, Link firstLink, Long wealth, String password) {
        ImportBucket bucket = setupTest(character, RandomStringUtils.randomAlphabetic(13), "1 0",
                new Space().withId(RandomUtils.nextLong(100, 200)),
                new Artifact().withId(RandomUtils.nextLong(200, 300)),
                "0 0", 0L, 0L, 0, 0L, new ArrayList<>());
        if (home == null) {
            home = new Space().withId(RandomUtils.nextLong(600, 700));
        }
        List<String> lines = bucket.getComponentLines().get(character.getId());
        setupTargetComponent(bucket, lines, home);
        setupTargetComponent(bucket, lines, firstLink);
        lines.add(wealth.toString());
        lines.add(password);
        return bucket;
    }

    private ImportBucket setupTest(Link link, List<Component> destinations, GameCharacter owner) {
        ImportBucket bucket = setupTest(link, RandomStringUtils.randomAlphabetic(13), "1 0",
                new Space().withId(RandomUtils.nextLong(100, 200)),
                new Artifact().withId(RandomUtils.nextLong(200, 300)),
                "0 0", 0L, 0L, 0, 0L, new ArrayList<>());
        if (owner == null) {
            owner = new GameCharacter().withId(RandomUtils.nextLong(500, 600));
        }
        List<String> lines = bucket.getComponentLines().get(link.getId());
        lines.add(String.valueOf(destinations.size()));
        for (Component component : destinations) {
            setupTargetComponent(bucket, lines, component);
        }
        setupTargetComponent(bucket, lines, owner);
        return bucket;
    }

    private ImportBucket setupTest(Program program, GameCharacter owner) {
        return setupTest(program, owner, false, false);
    }

    private ImportBucket setupTest(Program program, GameCharacter owner, boolean hashOwner, boolean blankOwner) {
        ImportBucket bucket = setupTest(program, RandomStringUtils.randomAlphabetic(13), "1 0",
                new Space().withId(RandomUtils.nextLong(100, 200)),
                new Artifact().withId(RandomUtils.nextLong(200, 300)),
                "0 0", 0L, 0L, 0, 0L, new ArrayList<>());
        if (owner == null) {
            owner = new GameCharacter().withId(RandomUtils.nextLong(500, 600));
        }
        List<String> lines = bucket.getComponentLines().get(program.getId());
        if (blankOwner) {
            lines.add("");
        } else {
            setupTargetComponent(bucket, lines, owner);
            if (hashOwner) {
                lines.set(lines.size() - 1, "#" + lines.get(lines.size() - 1));
            }
        }
        return bucket;
    }
    private ImportBucket setupTest(Space space, Space dropTo, Link firstLink, GameCharacter owner) {
        ImportBucket bucket = setupTest(space, RandomStringUtils.randomAlphabetic(13), "1 0",
                new Space().withId(RandomUtils.nextLong(100, 200)),
                new Artifact().withId(RandomUtils.nextLong(200, 300)),
                "0 0", 0L, 0L, 0, 0L, new ArrayList<>());
        if (owner == null) {
            owner = new GameCharacter().withId(RandomUtils.nextLong(500, 600));
        }
        List<String> lines = bucket.getComponentLines().get(space.getId());
        setupTargetComponent(bucket, lines, dropTo);
        setupTargetComponent(bucket, lines, firstLink);
        setupTargetComponent(bucket, lines, owner);
        return bucket;
    }

    private ImportBucket setupTest(Component component, String name, String description) {
        ImportBucket bucket = setupTest(component, name, description, new Space().withId(RandomUtils.nextLong(100, 200)),
                new Artifact().withId(RandomUtils.nextLong(200, 300)), "0 0", 0L, 0L, 0, 0L, new ArrayList<>());
        List<String> lines = bucket.getComponentLines().get(component.getId());
        lines.add("-1");
        lines.add("-1");
        setupTargetComponent(bucket, lines, new GameCharacter().withId(RandomUtils.nextLong(500, 600)));
        return bucket;
    }

    private void setupTargetComponent(ImportBucket bucket, List<String> lines, Component target) {
        if (target == null) {
            lines.add("-1");
        } else {
            lines.add(target.getId().toString());
            setupDefaultComponent(bucket, target);
        }
    }

    private ImportBucket setupTest(Component component, List<String> properties) {
        ImportBucket bucket = setupTest(component, RandomStringUtils.randomAlphabetic(13), "", new Space().withId(RandomUtils.nextLong(100, 200)),
                new Artifact().withId(RandomUtils.nextLong(200, 300)), "0 0", 0L, 0L, 0, 0L, properties);
        List<String> lines = bucket.getComponentLines().get(component.getId());
        lines.add("-1");
        lines.add("-1");
        setupTargetComponent(bucket, lines, new GameCharacter().withId(RandomUtils.nextLong(500, 600)));
        return bucket;
    }

    private ImportBucket setupTest(Component component, Integer useCount) {
        ImportBucket bucket = setupTest(component, RandomStringUtils.randomAlphabetic(13), "", new Space().withId(RandomUtils.nextLong(100, 200)),
                new Artifact().withId(RandomUtils.nextLong(200, 300)), "0 0", 0L, 0L, useCount, 0L, new ArrayList<>());
        List<String> lines = bucket.getComponentLines().get(component.getId());
        lines.add("-1");
        lines.add("-1");
        setupTargetComponent(bucket, lines, new GameCharacter().withId(RandomUtils.nextLong(500, 600)));
        return bucket;
    }

    private ImportBucket setupTest(Component component, Long created, Long lastUsed, Long lastModified) {
        ImportBucket bucket = setupTest(component, RandomStringUtils.randomAlphabetic(13), "", new Space().withId(RandomUtils.nextLong(100, 200)),
                new Artifact().withId(RandomUtils.nextLong(200, 300)), "0 0", created, lastUsed, 0, lastModified,
                new ArrayList<>());
        List<String> lines = bucket.getComponentLines().get(component.getId());
        lines.add("-1");
        lines.add("-1");
        setupTargetComponent(bucket, lines, new GameCharacter().withId(RandomUtils.nextLong(500, 600)));
        return bucket;
    }

    private ImportBucket setupTest(Space space, Artifact firstContent) {
        ImportBucket bucket = setupTest(space, RandomStringUtils.randomAlphabetic(13), "",
                new Space().withId(RandomUtils.nextLong(100, 200)),  firstContent, "0 0", 0L, 0L, 0, 0L,
                new ArrayList<>());
        List<String> lines = bucket.getComponentLines().get(space.getId());
        lines.add("-1");
        lines.add("-1");
        setupTargetComponent(bucket, lines, new GameCharacter().withId(RandomUtils.nextLong(500, 600)));
        return bucket;
    }

    private ImportBucket setupTest(Component component, Space location) {
        ImportBucket bucket = setupTest(component, RandomStringUtils.randomAlphabetic(13), "", location,
                         new Artifact().withId(RandomUtils.nextLong(200, 300)), "0 0", 0L, 0L, 0, 0L, new ArrayList<>());
        List<String> lines = bucket.getComponentLines().get(component.getId());
        lines.add("-1");
        lines.add("-1");
        setupTargetComponent(bucket, lines, new GameCharacter().withId(RandomUtils.nextLong(500, 600)));
        return bucket;
    }

    private ImportBucket setupTest(Component component, String name, String description, Space location,
                                   Artifact firstContent, String flags, Long created, Long lastUsed, Integer useCount,
                                   Long lastModified, List<String> properties) {
        if (StringUtils.isBlank(description)) {
            description = "description";
        }
        ImportBucket bucket = new ImportBucket();
        List<String> lines = new ArrayList<>();
        lines.add("#" + component.getId().toString());
        lines.add(name);
        lines.add(location.getId().toString());
        lines.add(firstContent.getId().toString());
        lines.add("-1");
        lines.add(flags);
        lines.add(created.toString());
        lines.add(lastUsed.toString());
        lines.add(useCount.toString());
        lines.add(lastModified.toString());
        lines.add("*Props*");
        lines.add("_/de:10:" + description);
        for (String property : properties) {
            lines.add(property);
        }
        lines.add("*End*");
        bucket.getComponents().put(component.getId(), component);
        bucket.getComponentLines().put(component.getId(), lines);

        setupDefaultComponent(bucket, location);
        setupDefaultComponent(bucket, firstContent);

        setupDefaultComponent(bucket, new GameCharacter().withId(1L));
        setupDefaultComponent(bucket, new Space().withId(0L));
        return bucket;
    }

    private void setupDefaultComponent(ImportBucket bucket, Component location) {
        bucket.getComponents().put(location.getId(), location);
        bucket.getComponentLines().put(location.getId(), defaultLines(location));
    }

    private List<String> defaultLines(Component component) {
        List<String> lines = new ArrayList<>();
        lines.add("#" + component.getId().toString());
        lines.add(RandomStringUtils.randomAlphabetic(13));
        lines.add("-1");
        lines.add("-1");
        lines.add("-1");
        lines.add("0 0");
        lines.add("0");
        lines.add("0");
        lines.add("0");
        lines.add("0");
        lines.add("*Props*");
        lines.add("_/de:10:description");
        lines.add("*End*");
        if (component instanceof Program) {
            lines.add("1");
        } else if (component instanceof GameCharacter) {
            lines.add("0");
            lines.add("-1");
            lines.add("0");
            lines.add("password");
        } else if (component instanceof Link) {
            lines.add("0");
            lines.add("1");
        } else if (component instanceof Space) {
            lines.add("-1");
            lines.add("-1");
            lines.add("1");
        } else if (component instanceof Artifact) {
            lines.add("0");
            lines.add("-1");
            lines.add("1");
            lines.add("0");
        }
        return lines;
    }
}
