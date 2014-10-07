package org.ruhlendavis.meta;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.ruhlendavis.meta.components.Artifact;
import org.ruhlendavis.meta.components.Link;
import org.ruhlendavis.meta.components.Space;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ComponentBuilderTest {
    private ComponentBuilder builder = new ComponentBuilder();
    private String databaseReference = RandomStringUtils.randomNumeric(5);
    private String ownerId = RandomStringUtils.randomNumeric(5);
    private String name = RandomStringUtils.randomAlphanumeric(13);
    private String description = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(10, 50));
    private Integer useCount = RandomUtils.nextInt(1, 400);

    @Test
    public void generateShouldNeverReturnNull() {
        assertNotNull(builder.generate(new ArrayList<String>()));
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
        List<String> input = generateInputWithOwner(ownerId);
        assertEquals(Long.parseLong(ownerId), builder.generate(input).getOwnerId(), 0);
    }

    @Test
    public void generateWithDatabaseReferenceOwnerShouldSetOwnerId() {
        String ownerDatabaseReference = "#" + ownerId;
        List<String> input = generateInputWithOwner(ownerDatabaseReference);
        assertEquals(Long.parseLong(ownerId), builder.generate(input).getOwnerId(), 0);
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

        List<String> coda = new ArrayList<String>(Arrays.asList(dropToReference, exitReference));
        List<String> input = generateInput("1 0", coda);
        Space space = (Space)builder.generate(input);
        assertEquals(Long.parseLong(dropToReference), space.getDropTo(), 0);
    }

    @Test
    public void generateShouldCreateAnArtifact() {
        List<String> input = generateInput("2 0");
        assertTrue(builder.generate(input) instanceof Artifact);
    }

    @Test
    public void generateShouldCreateALink() {
        List<String> input = generateInput("4 0");
        assertTrue(builder.generate(input) instanceof Link);
    }

    private List<String> generateInput() {
        List<String> coda = new ArrayList<String>(Arrays.asList("333", "444"));
        return generateInput("1 0", RandomStringUtils.randomNumeric(5), coda);
    }

    private List<String> generateInputWithOwner(String owner) {
        List<String> coda = new ArrayList<String>(Arrays.asList("333", "444"));
        return generateInput("1 0", owner, coda);
    }

    private List<String> generateInput(String flags) {
        return generateInput(flags, RandomStringUtils.randomNumeric(5), new ArrayList<String>());
    }

    private List<String> generateInput(String flags, List<String> coda) {
        return generateInput(flags, RandomStringUtils.randomNumeric(5), coda);
    }

    private List<String> generateInput(String flags, String owner, List<String> coda) {
        List<String> lines = new ArrayList<String>();
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
        lines.add(owner);
        return lines;
    }
}
