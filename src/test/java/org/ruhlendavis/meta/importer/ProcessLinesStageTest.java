package org.ruhlendavis.meta.importer;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.ruhlendavis.meta.GlobalConstants;
import org.ruhlendavis.meta.components.*;
import org.ruhlendavis.meta.components.Character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ProcessLinesStageTest {
    private ProcessLinesStage stage = new ProcessLinesStage();
    private String databaseReference = RandomStringUtils.randomNumeric(5);
    private String name = RandomStringUtils.randomAlphanumeric(13);
    private String description = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(10, 50));
    private String created = RandomStringUtils.randomNumeric(10);
    private String lastUsed = RandomStringUtils.randomNumeric(10);
    private String modified = RandomStringUtils.randomNumeric(10);
    private Long ownerId = RandomUtils.nextLong(100, 200);
    private Long componentId = RandomUtils.nextLong(0, 100);
    private Integer useCount = RandomUtils.nextInt(1, 400);

    private Component setupTest(List<String> input, Component component) {
        ImportBucket bucket = new ImportBucket();
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

    private List<String> generateInput() {
        List<String> coda = new ArrayList<>(Arrays.asList("333", "444", ownerId.toString()));
        return generateInput("0 0", coda);
    }

    private List<String> generateInput(Long ownerId) {
        List<String> coda = new ArrayList<>(Arrays.asList("333", "444", ownerId.toString()));
        return generateInput("0 0", coda);
    }

    private List<String> generateInputWithOwner(String ownerDatabaseReference) {
        return generateInput("0 0",  new ArrayList<>(Arrays.asList("333", "444", ownerDatabaseReference)));
    }

    private List<String> generateInput(String flags, List<String> coda) {
        List<String> lines = new ArrayList<>();
        lines.add("#" + databaseReference); // (00) Database Reference
        lines.add(name);                    // (01) Item Name
        lines.add("-1");                    // (02) Location
        lines.add("524");                   // (03) Contents
        lines.add("-1");                    // (04) Next
        lines.add(flags);                   // (05) Flags F2Flags
        lines.add(created);                 // (06) Created Timestamp
        lines.add(lastUsed);                // (07) LastUsed Timestamp
        lines.add(useCount.toString());     // (08) UseCount
        lines.add(modified);                // (09) LastModified
        lines.add("*Props*");               // (10) Beginning of property list
        lines.add("uno/-1/card1:2:56");     // Some other property
        lines.add("_/de:10:" + description);// (??) Description
        lines.add("*End*");                 // (??) End of property list

        for (String line : coda) {
            lines.add(line);
        }
        return lines;
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

    //
//    @Test
//    public void generateWithSpaceShouldSetDropTo() {
//        String dropToReference = RandomStringUtils.randomNumeric(5);
//
//        List<String> input = generateInput("0 0", new ArrayList<>(Arrays.asList(dropToReference, "", ownerId.toString())));
//        Space space = (Space) stage.generate(input);
//        assertEquals(Long.parseLong(dropToReference), space.getDropTo(), 0);
//    }
//
//    @Test
//    public void generateShouldSetSpaceFirstLink() {
//        Long linkId = RandomUtils.nextLong(0, 10000);
//        List<String> input = generateInput("0 0", new ArrayList<>(Arrays.asList("", linkId.toString(), "")));
//        Space space = (Space) stage.generate(input);
//        assertEquals(linkId, space.getLinks().get(0).getId(), 0);
//    }
//
//    @Test
//    public void generateShouldNotSetSpaceFirstLink() {
//        List<String> input = generateInput("0 0", new ArrayList<>(Arrays.asList("", "-1", "")));
//        Space space = (Space) stage.generate(input);
//        assertEquals(0, space.getLinks().size());
//    }
//
//    @Test
//    public void generateShouldSetAnArtifactValue() {
//        Long value = RandomUtils.nextLong(0, 10000);
//        List<String> coda = new ArrayList<>(Arrays.asList("333", "444", ownerId.toString(), value.toString()));
//        List<String> input = generateInput("1 0", coda);
//        Artifact artifact = (Artifact) stage.generate(input);
//        assertEquals(value, artifact.getValue(), 0);
//    }
//
//    @Test
//    public void generateShouldSetAnArtifactHome() {
//        Long home = RandomUtils.nextLong(0, 10000);
//        List<String> input = generateInput("1 0", new ArrayList<>(Arrays.asList(home.toString(), "", "", "")));
//        Artifact artifact = (Artifact) stage.generate(input);
//        assertEquals(home, artifact.getHome(), 0);
//    }
//
//    @Test
//    public void generateShouldSetArtifactFirstLink() {
//        Long linkId = RandomUtils.nextLong(0, 10000);
//        List<String> input = generateInput("1 0", new ArrayList<>(Arrays.asList("", linkId.toString(), "", "")));
//        Artifact artifact = (Artifact) stage.generate(input);
//        assertEquals(linkId, artifact.getLinks().get(0).getId(), 0);
//    }
//
//    @Test
//    public void generateShouldNotSetArtifactFirstLink() {
//        List<String> input = generateInput("1 0", new ArrayList<>(Arrays.asList("", "-1", "", "")));
//        Artifact artifact = (Artifact) stage.generate(input);
//        assertEquals(0, artifact.getLinks().size());
//    }
//
//    @Test
//    public void generateShouldSetLinkOwner() {
//        List<String> input = generateInput("2 0", new ArrayList<>(Arrays.asList("0", ownerId.toString())));
//        Link link = (Link) stage.generate(input);
//        assertEquals(ownerId, link.getOwnerId(), 0);
//    }
//
//    @Test
//    public void generateShouldSetOneLinkDestinationId() {
//        Long destinationId = RandomUtils.nextLong(1, 10000);
//        List<String> input = generateInput("2 0", new ArrayList<>(Arrays.asList("1", destinationId.toString(), "")));
//        Link link = (Link) stage.generate(input);
//        assertEquals(destinationId, link.getDestinationIds().get(0), 0);
//    }
//
//    @Test
//    public void generateShouldSetMultipleLinkDestinationIds() {
//        Integer idCount = RandomUtils.nextInt(5, 10);
//        List<String> coda = new ArrayList<>();
//        List<Long> destinationIds = new ArrayList<>();
//        coda.add(idCount.toString());
//        for (int i = 0; i < idCount; i++) {
//            Long destinationId = RandomUtils.nextLong(1, 10000);
//            coda.add(destinationId.toString());
//            destinationIds.add(destinationId);
//        }
//        coda.add("");
//        List<String> input = generateInput("2 0", coda);
//        Link link = (Link) stage.generate(input);
//        for (int i = 0; i < idCount; i++) {
//            assertEquals(destinationIds.get(i), link.getDestinationIds().get(i), 0);
//        }
//    }
//
//    @Test
//    public void generateShouldSetACharacterHome() {
//        Long home = RandomUtils.nextLong(1, 10000);
//        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList(home.toString(), "", "", "")));
//        Character character = (Character) stage.generate(input);
//        assertEquals(home, character.getHome(), 0);
//    }
//
//    @Test
//    public void generateShouldSetACharacterWealth() {
//        Long wealth = RandomUtils.nextLong(1, 10000);
//        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", "", wealth.toString(), "")));
//        Character character = (Character) stage.generate(input);
//        assertEquals(wealth, character.getWealth(), 0);
//    }
//
//    @Test
//    public void generateShouldSetACharacterPassword() {
//        String password = RandomStringUtils.randomAlphabetic(13);
//        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", "", "", password)));
//        Character character = (Character) stage.generate(input);
//        assertEquals(password, character.getPassword());
//    }
//
//    @Test
//    public void generateShouldSetCharacterFirstLink() {
//        Long linkId = RandomUtils.nextLong(0, 10000);
//        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", linkId.toString(), "", "")));
//        Character character = (Character) stage.generate(input);
//        assertEquals(linkId, character.getLinks().get(0).getId(), 0);
//    }
//
//    @Test
//    public void generateShouldNotSetCharacterFirstLink() {
//        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", "-1", "", "")));
//        Character character = (Character) stage.generate(input);
//        assertEquals(0, character.getLinks().size());
//    }
//
//    @Test
//    public void generateShouldAddPropertyToProperties() {
//        String path = RandomStringUtils.randomAlphabetic(8);
//        String value = RandomStringUtils.randomAlphabetic(7);
//
//        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", "-1", "", "")));
//        Character character = (Character) stage.generate(input);
//        StringProperty property = (StringProperty)character.getProperties().getProperty(path);
//        assertEquals(value, property.getValue());
//    }
//

}
