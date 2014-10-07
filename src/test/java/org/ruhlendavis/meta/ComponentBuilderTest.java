package org.ruhlendavis.meta;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.ruhlendavis.meta.components.Artifact;
import org.ruhlendavis.meta.components.Space;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ComponentBuilderTest {
    private ComponentBuilder builder = new ComponentBuilder();
    private String databaseReference = RandomStringUtils.randomNumeric(5);
    private String ownerDatabaseReference = RandomStringUtils.randomNumeric(5);
    private String name = RandomStringUtils.randomAlphanumeric(13);
    private String description = RandomStringUtils.randomAlphanumeric(RandomUtils.nextInt(10, 50));
    private Integer useCount = RandomUtils.nextInt(1, 400);

    @Test
    public void generateShouldNeverReturnNull() {
        assertNotNull(builder.generate(null));
        assertNotNull(builder.generate(""));
    }

    @Test
    public void generateShouldSetId() {
        String input = generateInput("1 0", "");
        assertEquals(Long.parseLong(databaseReference), builder.generate(input).getId(), 0);
    }

    @Test
    public void generateShouldSetName() {
        String input = generateInput("1 0", "");
        assertEquals(name, builder.generate(input).getName());
    }

    @Test
    public void generateShouldSetCreatedTimeStamp() {
        String input = generateInput("1 0", "");
        assertEquals(1029616068, builder.generate(input).getCreated().getEpochSecond());
    }

    @Test
    public void generateShouldSetLastUsedTimeStamp() {
        String input = generateInput("1 0", "");
        assertEquals(1412145773, builder.generate(input).getLastUsed().getEpochSecond());
    }

    @Test
    public void generateShouldSetModifiedTimeStamp() {
        String input = generateInput("1 0", "");
        assertEquals(1411597664, builder.generate(input).getLastModified().getEpochSecond());
    }

    @Test
    public void generateShouldSetUseCount() {
        String input = generateInput("1 0", "");
        assertEquals(useCount, builder.generate(input).getUseCount(), 0);
    }

    @Test
    public void generateShouldSetDescription() {
        String input = generateInput("1 0", "");
        assertEquals(description, builder.generate(input).getDescription());
    }

    @Test
    public void generateShouldSetOwnerId() {
        String input = generateInput("1 0", "");
        assertEquals(Long.parseLong(ownerDatabaseReference), builder.generate(input).getOwnerId(), 0);
    }

    @Test
    public void generateShouldCreateASpace() {
        String input = generateInput("1 0", "");
        assertTrue(builder.generate(input) instanceof Space);
    }

    @Test
    public void generateShouldCreateAnArtifact() {
        String input = generateInput("2 0", "");
        assertTrue(builder.generate(input) instanceof Artifact);
    }

    private String generateInput(String flags, String coda) {
        return "#" + databaseReference + "\n" +     // (0) Database Reference
                name + "\n" +                       // (1) Item Name
                "-1\n" +                            // (2) Location
                "524\n" +                           // (3) Contents
                "-1\n" +                            // (4) Next
                flags + "\n" +                      // (5) Flags F2Flags
                "1029616068\n" +                    // (6) Created Timestamp
                "1412145773\n" +                    // (7) LastUsed Timestamp
                useCount.toString() + "\n" +        // (8) UseCount
                "1411597664\n" +                    // (9) LastModified
                "*Props*\n" +                       // (10) Beginning of property list
                "_/de:10:" + description + "\n" +   // (?) Description
                "*End*\n" +                         // (?) End of property list
                coda + "\n" +                       // Type specific coda
                ownerDatabaseReference + "\n"
                ;
    }
}
