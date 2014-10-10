package org.ruhlendavis.meta.importer;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.ruhlendavis.meta.GlobalConstants;
import org.ruhlendavis.meta.components.*;
import org.ruhlendavis.meta.components.Character;
import org.ruhlendavis.meta.properties.Property;
import org.ruhlendavis.meta.properties.StringProperty;

import java.util.*;

import static org.junit.Assert.*;

public class ProcessLinesStageTest {
    private ProcessLinesStage stage = new ProcessLinesStage();
    private String databaseReference = RandomStringUtils.randomNumeric(5);
    private String name = RandomStringUtils.randomAlphanumeric(13);
    private Long locationId = RandomUtils.nextLong(200, 300);
    private Long contentsId = RandomUtils.nextLong(300, 400);
    private String description = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(10, 50));
    private String created = RandomStringUtils.randomNumeric(10);
    private String lastUsed = RandomStringUtils.randomNumeric(10);
    private String modified = RandomStringUtils.randomNumeric(10);
    private Long ownerId = RandomUtils.nextLong(100, 200);
    private Long componentId = RandomUtils.nextLong(0, 100);
    private Integer useCount = RandomUtils.nextInt(1, 400);
    private ImportBucket bucket = new ImportBucket();

    private Component setupTest(List<String> input, Component component) {
        setupSpecialComponents(bucket);

        Component owner = new Character();
        owner.setId(ownerId);
        bucket.getComponents().put(ownerId, owner);

        component.setId(componentId);
        bucket.getComponents().put(componentId, component);
        bucket.getComponentLines().put(componentId, input);

        stage.run(bucket);
        return component;
    }

    @Test
    public void generateShouldSetName() {
        Component component = setupTest(generateInput(), new Space());
        assertEquals(name, component.getName());
    }

    @Test
    public void generateShouldSetLocation() {
        Space space = new Space();
        space.setId(locationId);
        bucket.getComponents().put(locationId, space);
        Component component = setupTest(generateInput(), new Space());
        assertEquals(locationId, component.getLocation().getId());
    }

    @Test
    public void generateShouldSetFirstContents() {
        Component component = new Component();
        component.setId(contentsId);
        bucket.getComponents().put(contentsId, component);
        component = setupTest(generateInput(), new Space());
        assertEquals(contentsId, component.getContents().get(0).getId());
    }

    @Test
    public void generateShouldSetCreatedTimeStamp() {
        Component component = setupTest(generateInput(), new Space());
        assertEquals(Long.parseLong(created), component.getCreated().getEpochSecond());
    }

    @Test
    public void generateShouldSetLastUsedTimeStamp() {
        Component component = setupTest(generateInput(), new Space());
        assertEquals(Long.parseLong(lastUsed), component.getLastUsed().getEpochSecond());
    }

    @Test
    public void generateShouldSetModifiedTimeStamp() {
        Component component = setupTest(generateInput(), new Space());
        assertEquals(Long.parseLong(modified), component.getLastModified().getEpochSecond());
    }

    @Test
    public void generateShouldSetUseCount() {
        Component component = setupTest(generateInput(), new Space());
        assertEquals(useCount, component.getUseCount(), 0);
    }

    @Test
    public void generateShouldSetDescription() {
        Component component = setupTest(generateInput(), new Space());
        assertEquals(description, component.getDescription());
    }

    @Test
    public void generateShouldSetOwner() {
        Component component = setupTest(generateInput(), new Space());
        assertEquals(ownerId, component.getOwner().getId(), 0);
    }

    @Test
    public void generateWithDatabaseReferenceOwnerShouldSetOwnerId() {
        String ownerDatabaseReference = "#" + ownerId;
        Component component = setupTest(generateInputWithOwner(ownerDatabaseReference), new Space());
        assertEquals(ownerId, component.getOwner().getId(), 0);
    }

    @Test
    public void generateWithBlankOwnerShouldSetOwnerId() {
        Component component = setupTest(generateInputWithOwner(""), new Space());
        assertEquals(GlobalConstants.REFERENCE_UNKNOWN, component.getOwner().getId(), 0);
    }

    @Test
    public void generateWithSpaceShouldSetDropTo() {
        Long dropToReference = RandomUtils.nextLong(200, 300);
        Component dropTo = new Component();
        dropTo.setId(dropToReference);
        bucket.getComponents().put(dropToReference, dropTo);

        List<String> input = generateInput("0 0", new ArrayList<>(Arrays.asList(dropToReference.toString(), "", ownerId.toString())));
        Space space = (Space)setupTest(input, new Space());
        assertEquals(dropToReference, space.getDropTo().getId(), 0);
    }

    @Test
    public void generateShouldSetSpaceFirstLink() {
        Long linkId = RandomUtils.nextLong(200, 300);
        Link link = new Link();
        link.setId(linkId);
        bucket.getComponents().put(linkId, link);
        List<String> input = generateInput("0 0", new ArrayList<>(Arrays.asList("", linkId.toString(), "")));
        Space space = (Space)setupTest(input, new Space());
        assertEquals(linkId, space.getLinks().get(0).getId(), 0);
    }

    @Test
    public void generateShouldNotSetSpaceFirstLink() {
        List<String> input = generateInput("0 0", new ArrayList<>(Arrays.asList("", "-1", "")));
        Space space = (Space)setupTest(input, new Space());
        assertEquals(0, space.getLinks().size());
    }

    @Test
    public void generateShouldSetAnArtifactValue() {
        Long value = RandomUtils.nextLong(5, 10000);
        List<String> coda = new ArrayList<>(Arrays.asList("333", "444", ownerId.toString(), value.toString()));
        List<String> input = generateInput("1 0", coda);
        Artifact artifact = (Artifact) setupTest(input, new Artifact());
        assertEquals(value, artifact.getValue(), 0);
    }

    @Test
    public void generateShouldSetAnArtifactHome() {
        Long homeId = RandomUtils.nextLong(200, 300);
        Space home = new Space();
        home.setId(homeId);
        bucket.getComponents().put(homeId, home);
        List<String> input = generateInput("1 0", new ArrayList<>(Arrays.asList(homeId.toString(), "", "", "")));
        Artifact artifact = (Artifact) setupTest(input, new Artifact());
        assertEquals(homeId, artifact.getHome().getId(), 0);
    }

    @Test
    public void generateShouldSetArtifactFirstLink() {
        Long linkId = RandomUtils.nextLong(200, 300);
        Link link = new Link();
        link.setId(linkId);
        bucket.getComponents().put(linkId, link);
        List<String> input = generateInput("1 0", new ArrayList<>(Arrays.asList("", linkId.toString(), "", "")));
        Artifact artifact = (Artifact) setupTest(input, new Artifact());
        assertEquals(linkId, artifact.getLinks().get(0).getId(), 0);
    }

    @Test
    public void generateShouldNotSetArtifactFirstLink() {
        List<String> input = generateInput("1 0", new ArrayList<>(Arrays.asList("", "-1", "", "")));
        Artifact artifact = (Artifact) setupTest(input, new Artifact());
        assertEquals(0, artifact.getLinks().size());
    }

    @Test
    public void generateShouldSetLinkOwner() {
        List<String> input = generateInput("2 0", new ArrayList<>(Arrays.asList("0", ownerId.toString())));
        Link link = (Link) setupTest(input, new Link());
        assertEquals(ownerId, link.getOwner().getId(), 0);
    }

    @Test
    public void generateShouldSetOneLinkDestinationId() {
        Long destinationId = RandomUtils.nextLong(200, 300);
        Component component = new Component();
        component.setId(destinationId);
        bucket.getComponents().put(destinationId, component);
        List<String> input = generateInput("2 0", new ArrayList<>(Arrays.asList("1", destinationId.toString(), "")));
        Link link = (Link) setupTest(input, new Link());
        assertEquals(destinationId, link.getDestinations().get(0).getId(), 0);
    }

    @Test
    public void generateShouldSetMultipleLinkDestinationIds() {
        Integer idCount = RandomUtils.nextInt(5, 10);
        List<String> coda = new ArrayList<>();
        List<Long> destinationIds = new ArrayList<>();
        coda.add(idCount.toString());
        for (int i = 0; i < idCount; i++) {
            Long destinationId = 200L+i;
            coda.add(destinationId.toString());
            Component component = new Component();
            component.setId(destinationId);
            bucket.getComponents().put(destinationId, component);
            destinationIds.add(destinationId);
        }
        coda.add("");
        List<String> input = generateInput("2 0", coda);
        Link link = (Link) setupTest(input, new Link());
        for (int i = 0; i < idCount; i++) {
            assertEquals(destinationIds.get(i), link.getDestinations().get(i).getId(), 0);
        }
    }

    @Test
    public void generateShouldSetACharacterHome() {
        Long homeId = RandomUtils.nextLong(200, 300);
        Space space = new Space();
        space.setId(homeId);
        bucket.getComponents().put(homeId, space);
        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList(homeId.toString(), "", "", "")));
        Character character = (Character)setupTest(input, new Character());
        assertEquals(homeId, character.getHome().getId(), 0);
    }

    @Test
    public void generateShouldSetACharacterWealth() {
        Long wealth = RandomUtils.nextLong(1, 10000);
        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", "", wealth.toString(), "")));
        Character character = (Character) setupTest(input, new Character());
        assertEquals(wealth, character.getWealth(), 0);
    }

    @Test
    public void generateShouldSetACharacterPassword() {
        String password = RandomStringUtils.randomAlphabetic(13);
        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", "", "", password)));
        Character character = (Character) setupTest(input, new Character());
        assertEquals(password, character.getPassword());
    }

    @Test
    public void generateShouldSetCharacterFirstLink() {
        Long linkId = RandomUtils.nextLong(200, 300);
        Link link = new Link();
        link.setId(linkId);
        bucket.getComponents().put(linkId, link);
        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", linkId.toString(), "", "")));
        Character character = (Character) setupTest(input, new Character());
        assertEquals(linkId, character.getLinks().get(0).getId(), 0);
    }

    @Test
    public void generateShouldNotSetCharacterFirstLink() {
        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", "-1", "", "")));
        Character character = (Character) setupTest(input, new Character());
        assertEquals(0, character.getLinks().size());
    }

    @Test
    public void generateShouldAddStringPropertyToProperties() {
        String path = RandomStringUtils.randomAlphabetic(8);
        String value = RandomStringUtils.randomAlphabetic(7);
        StringProperty expected = new StringProperty();
        expected.setValue(value);
        Map<String, Property> properties = new HashMap<>();
        properties.put(path, expected);
        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", "-1", "", "")), properties);
        Character character = (Character) setupTest(input, new Character());
        StringProperty property = (StringProperty)character.getProperties().getProperty(path);
        assertEquals(value, property.getValue());
    }

    @Test
    public void generateShouldAddMultipleStringPropertiesToProperties() {
        Map<String, Property> properties = new HashMap<>();

        String path1 = RandomStringUtils.randomAlphabetic(8);
        StringProperty expected1 = new StringProperty().withValue(RandomStringUtils.randomAlphabetic(7));
        properties.put(path1, expected1);

        String path2 = RandomStringUtils.randomAlphabetic(8);
        StringProperty expected2 = new StringProperty().withValue(RandomStringUtils.randomAlphabetic(7));
        properties.put(path2, expected2);

        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", "-1", "", "")), properties);
        Character character = (Character) setupTest(input, new Character());

        StringProperty property = (StringProperty)character.getProperties().getProperty(path1);
        assertEquals(expected1.getValue(), property.getValue());

        property = (StringProperty)character.getProperties().getProperty(path2);
        assertEquals(expected2.getValue(), property.getValue());
    }

    private List<String> generateInput() {
        List<String> coda = new ArrayList<>(Arrays.asList("333", "444", ownerId.toString()));
        return generateInput("0 0", coda);
    }

    private List<String> generateInputWithOwner(String ownerDatabaseReference) {
        return generateInput("0 0",  new ArrayList<>(Arrays.asList("333", "444", ownerDatabaseReference)));
    }

    private List<String> generateInput(String flags, List<String> coda) {
        return generateInput(flags, coda, new HashMap<>());
    }

    private List<String> generateInput(String flags, List<String> coda, Map<String, Property> properties) {
        List<String> lines = new ArrayList<>();
        lines.add("#" + databaseReference); // (00) Database Reference
        lines.add(name);                    // (01) Item Name
        lines.add(locationId.toString());   // (02) Location
        lines.add(contentsId.toString());   // (03) Contents
        lines.add("-1");                    // (04) Next
        lines.add(flags);                   // (05) Flags F2Flags
        lines.add(created);                 // (06) Created Timestamp
        lines.add(lastUsed);                // (07) LastUsed Timestamp
        lines.add(useCount.toString());     // (08) UseCount
        lines.add(modified);                // (09) LastModified
        lines.add("*Props*");               // (10) Beginning of property list
        lines.add("_/de:10:" + description);// (??) Description
        addProperties(lines, properties);
        lines.add("*End*");                 // (??) End of property list

        for (String line : coda) {
            lines.add(line);
        }
        return lines;
    }

    private void addProperties(List<String> lines, Map<String, Property> properties) {
        for (Map.Entry entry : properties.entrySet()) {
            String line = entry.getKey() + ":";
            if (entry.getValue() instanceof StringProperty) {
                line = line + "10:" + ((StringProperty) entry.getValue()).getValue();
                lines.add(line);
            }
        }
    }

    private void setupSpecialComponents(ImportBucket bucket) {
        Component component = new Component();
        component.setId(GlobalConstants.REFERENCE_UNKNOWN);
        bucket.getComponents().put(GlobalConstants.REFERENCE_UNKNOWN, component);
        component = new Component();
        component.setId(GlobalConstants.REFERENCE_AMBIGUOUS);
        bucket.getComponents().put(GlobalConstants.REFERENCE_AMBIGUOUS, component);
        component = new Component();
        component.setId(GlobalConstants.REFERENCE_HOME);
        bucket.getComponents().put(GlobalConstants.REFERENCE_HOME, component);
    }
}
