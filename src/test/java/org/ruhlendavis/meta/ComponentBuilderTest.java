package org.ruhlendavis.meta;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.ruhlendavis.meta.components.Artifact;
import org.ruhlendavis.meta.components.Character;
import org.ruhlendavis.meta.components.Link;
import org.ruhlendavis.meta.components.Space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ComponentBuilderTest {
    private ComponentBuilder builder = new ComponentBuilder();
    private String databaseReference = RandomStringUtils.randomNumeric(5);
    private Long ownerId = RandomUtils.nextLong(1, 100000);
    private String name = RandomStringUtils.randomAlphanumeric(13);
    private String description = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(10, 50));
    private Integer useCount = RandomUtils.nextInt(1, 400);

    @Test
    public void generateShouldNeverReturnNull() {
        assertNotNull(builder.generate(new ArrayList<>()));
    }

    @Test
    public void generateShouldSetId() {
        List<String> input = generateInput();
        assertEquals(Long.parseLong(databaseReference), builder.generate(input).getId(), 0);
    }

    @Test
    public void generateShouldSetName() {
        List<String> input = generateInput();
        assertEquals(name, builder.generate(input).getName());
    }

    @Test
    public void generateShouldSetCreatedTimeStamp() {
        List<String> input = generateInput();
        assertEquals(1029616068, builder.generate(input).getCreated().getEpochSecond());
    }

    @Test
    public void generateShouldSetLastUsedTimeStamp() {
        List<String> input = generateInput();
        assertEquals(1412145773, builder.generate(input).getLastUsed().getEpochSecond());
    }

    @Test
    public void generateShouldSetModifiedTimeStamp() {
        List<String> input = generateInput();
        assertEquals(1411597664, builder.generate(input).getLastModified().getEpochSecond());
    }

    @Test
    public void generateShouldSetUseCount() {
        List<String> input = generateInput();
        assertEquals(useCount, builder.generate(input).getUseCount(), 0);
    }

    @Test
    public void generateShouldSetDescription() {
        List<String> input = generateInput();
        assertEquals(description, builder.generate(input).getDescription());
    }

    @Test
    public void generateShouldSetOwnerId() {
        List<String> input = generateInput(ownerId);
        assertEquals(ownerId, builder.generate(input).getOwnerId(), 0);
    }

    @Test
    public void generateWithDatabaseReferenceOwnerShouldSetOwnerId() {
        String ownerDatabaseReference = "#" + ownerId;
        List<String> input = generateInputWithOwner(ownerDatabaseReference);
        assertEquals(ownerId, builder.generate(input).getOwnerId(), 0);
    }

    @Test
    public void generateWithBlankOwnerShouldSetOwnerId() {
        List<String> input = generateInputWithOwner("");
        assertEquals(0L, builder.generate(input).getOwnerId(), 0);
    }

    @Test
    public void generateShouldCreateASpace() {
        List<String> input = generateInput();
        assertTrue(builder.generate(input) instanceof Space);
    }

    @Test
    public void generateWithSpaceShouldSetDropTo() {
        String dropToReference = RandomStringUtils.randomNumeric(5);
        String exitReference = RandomStringUtils.randomNumeric(5);

        List<String> coda = new ArrayList<>(Arrays.asList(dropToReference, exitReference, ownerId.toString()));
        List<String> input = generateInput("0 0", coda);
        Space space = (Space)builder.generate(input);
        assertEquals(Long.parseLong(dropToReference), space.getDropTo(), 0);
    }

    @Test
    public void generateShouldCreateAnArtifact() {
        List<String> coda = new ArrayList<>(Arrays.asList("333", "444", ownerId.toString(), "333"));
        List<String> input = generateInput("1 0", coda);
        assertTrue(builder.generate(input) instanceof Artifact);
    }

    @Test
    public void generateShouldSetAnArtifactValue() {
        Long value = RandomUtils.nextLong(0, 10000);
        List<String> coda = new ArrayList<>(Arrays.asList("333", "444", ownerId.toString(), value.toString()));
        List<String> input = generateInput("1 0", coda);
        Artifact artifact = (Artifact)builder.generate(input);
        assertEquals(value, artifact.getValue(), 0);
    }

    @Test
    public void generateShouldSetAnArtifactHome() {
        Long home = RandomUtils.nextLong(0, 10000);
        List<String> input = generateInput("1 0", new ArrayList<>(Arrays.asList(home.toString(), "", "", "")));
        Artifact artifact = (Artifact)builder.generate(input);
        assertEquals(home, artifact.getHome(), 0);
    }

    @Test
    public void generateShouldSetArtifactFirstLink() {
        Long linkId = RandomUtils.nextLong(0, 10000);
        List<String> input = generateInput("1 0", new ArrayList<>(Arrays.asList("", linkId.toString(), "", "")));
        Artifact artifact = (Artifact)builder.generate(input);
        assertEquals(linkId, artifact.getLinks().get(0).getId(), 0);
    }

    @Test
    public void generateShouldNotSetArtifactFirstLink() {
        List<String> input = generateInput("1 0", new ArrayList<>(Arrays.asList("", "-1", "", "")));
        Artifact artifact = (Artifact)builder.generate(input);
        assertEquals(0, artifact.getLinks().size());
    }

    @Test
    public void generateShouldCreateALink() {
        List<String> input = generateInput("2 0");
        assertTrue(builder.generate(input) instanceof Link);
    }

    @Test
    public void generateShouldCreateACharacter() {
        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", "", "", "")));
        assertTrue(builder.generate(input) instanceof Character);
    }

    @Test
    public void generateShouldSetACharacterHome() {
        Long home = RandomUtils.nextLong(1, 10000);
        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList(home.toString(), "", "", "")));
        Character character = (Character)builder.generate(input);
        assertEquals(home, character.getHome(), 0);
    }

    @Test
    public void generateShouldSetACharacterWealth() {
        Long wealth = RandomUtils.nextLong(1, 10000);
        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", "", wealth.toString(), "")));
        Character character = (Character)builder.generate(input);
        assertEquals(wealth, character.getWealth(), 0);
    }

    @Test
    public void generateShouldSetACharacterPassword() {
        String password = RandomStringUtils.randomAlphabetic(13);
        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", "", "", password)));
        Character character = (Character)builder.generate(input);
        assertEquals(password, character.getPassword());
    }

    @Test
    public void generateShouldSetCharacterFirstLink() {
        Long linkId = RandomUtils.nextLong(0, 10000);
        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", linkId.toString(), "", "")));
        Character character = (Character)builder.generate(input);
        assertEquals(linkId, character.getLinks().get(0).getId(), 0);
    }

    @Test
    public void generateShouldNotSetCharacterFirstLink() {
        List<String> input = generateInput("3 0", new ArrayList<>(Arrays.asList("", "-1", "", "")));
        Character character = (Character)builder.generate(input);
        assertEquals(0, character.getLinks().size());
    }

    private List<String> generateInput() {
        List<String> coda = new ArrayList<>(Arrays.asList("333", "444", ownerId.toString()));
        return generateInput("0 0", coda);
    }

    private List<String> generateInput(String flags) {
        return generateInput(flags, new ArrayList<>(Arrays.asList("333", "444", ownerId.toString())));
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
        lines.add("1029616068");            // (06) Created Timestamp
        lines.add("1412145773");            // (07) LastUsed Timestamp
        lines.add(useCount.toString());     // (08) UseCount
        lines.add("1411597664");            // (09) LastModified
        lines.add("*Props*");               // (10) Beginning of property list
        lines.add("_/de:10:" + description);// (??) Description
        lines.add("*End*");                 // (??) End of property list

        for (String line : coda) {
            lines.add(line);
        }
        return lines;
    }
}
