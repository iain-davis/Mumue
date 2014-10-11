package org.ruhlendavis.meta.importer;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SeparateSectionsStageTest {
    private SeparateSectionsStage stage = new SeparateSectionsStage();

    @Test
    public void runHandlesNoLines() {
        ImportBucket bucket = new ImportBucket();
        bucket.setSourceLines(new ArrayList<>());
        stage.run(bucket);
        assertTrue(bucket.isFailed());
    }

    @Test
    public void runConfirmsDatabaseVersion() {
        ImportBucket bucket = new ImportBucket();
        List<String> lines = new ArrayList<>();
        lines.add(RandomStringUtils.randomAlphabetic(25));
        lines.add(RandomStringUtils.randomNumeric(5));
        lines.add("1");
        bucket.setSourceLines(lines);
        stage.run(bucket);
        assertTrue(bucket.isFailed());
    }

    @Test
    public void runConfirmsDatabaseFormat() {
        ImportBucket bucket = new ImportBucket();
        List<String> lines = new ArrayList<>();
        lines.add("***Foxen5 TinyMUCK DUMP Format***");
        lines.add(RandomStringUtils.randomNumeric(5));
        lines.add("3");
        bucket.setSourceLines(lines);
        stage.run(bucket);
        assertTrue(bucket.isFailed());
    }

    @Test
    public void runRetrievesDatabaseItemCount() {
        String parameterCount = RandomStringUtils.randomNumeric(2);
        String itemCount = RandomStringUtils.randomNumeric(2);
        ImportBucket bucket = setupBucket(itemCount, parameterCount);
        addOneDatabaseItemToBucket(bucket, "#1", "0", 0, 3);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(Long.parseLong(itemCount), bucket.getDatabaseItemCount(), 0);
    }

    @Test
    public void runRetrievesParameterCount() {
        String parameterCount = RandomStringUtils.randomNumeric(2);
        String itemCount = RandomStringUtils.randomNumeric(2);
        ImportBucket bucket = setupBucket(itemCount, parameterCount);
        addOneDatabaseItemToBucket(bucket, "#1", "0", 0, 3);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(Long.parseLong(parameterCount), bucket.getParameterCount(), 0);
    }

    @Test
    public void runRetrievesParameterLines() {
        String parameterCount = RandomStringUtils.randomNumeric(2);
        String itemCount = RandomStringUtils.randomNumeric(5);
        ImportBucket bucket = setupBucket(itemCount, parameterCount);
        int count = Integer.parseInt(parameterCount);
        addOneDatabaseItemToBucket(bucket, "#1", "0", 0, 3);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(count, bucket.getParameterCount(), 0);
        assertEquals(count, bucket.getParameterLines().size(), 0);
    }

    @Test
    public void runClearsSourceLinesWhenFinished() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToBucket(bucket, id, "0", 0, SeparateSectionsStage.SPACE_CODA_LINES);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(0, bucket.getSourceLines().size());
    }

    @Test
    public void runRetrievesOneSpaceItem() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToBucket(bucket, id, "0", 0, SeparateSectionsStage.SPACE_CODA_LINES);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(1, bucket.getComponentLines().size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id)));
        assertEquals(15, bucket.getComponentLines().get(Long.parseLong(id)).size());
    }

    @Test
    public void runRetrievesOneSpaceItemWhenItemHasFlags() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToBucket(bucket, id, "8", 0, SeparateSectionsStage.SPACE_CODA_LINES);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(1, bucket.getComponentLines().size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id)));
        assertEquals(15, bucket.getComponentLines().get(Long.parseLong(id)).size());
    }

    @Test
    public void runRetrievesThreeSpaceItems() {
        ImportBucket bucket = setupBucket("1", "1");
        String id1 = RandomStringUtils.randomNumeric(3);
        String id2 = RandomStringUtils.randomNumeric(3);
        String id3 = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToBucket(bucket, id1, "0", 0, SeparateSectionsStage.SPACE_CODA_LINES);
        addOneDatabaseItemToBucket(bucket, id2, "0", 1, SeparateSectionsStage.SPACE_CODA_LINES);
        addOneDatabaseItemToBucket(bucket, id3, "0", 2, SeparateSectionsStage.SPACE_CODA_LINES);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(3, bucket.getComponentLines().size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id1)));
        assertEquals(15, bucket.getComponentLines().get(Long.parseLong(id1)).size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id2)));
        assertEquals(16, bucket.getComponentLines().get(Long.parseLong(id2)).size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id3)));
        assertEquals(17, bucket.getComponentLines().get(Long.parseLong(id3)).size());
    }

    @Test
    public void runRetrievesOneArtifactItem() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToBucket(bucket, id, "1", 0, SeparateSectionsStage.ARTIFACT_CODA_LINES);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(1, bucket.getComponentLines().size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id)));
        assertEquals(16, bucket.getComponentLines().get(Long.parseLong(id)).size());
    }

    @Test
    public void runRetrievesOneArtifactItemWhenItemHasFlags() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToBucket(bucket, id, "9", 0, SeparateSectionsStage.ARTIFACT_CODA_LINES);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(1, bucket.getComponentLines().size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id)));
        assertEquals(16, bucket.getComponentLines().get(Long.parseLong(id)).size());
    }

    @Test
    public void runRetrievesOneCharacterItem() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToBucket(bucket, id, "3", 0, SeparateSectionsStage.CHARACTER_CODA_LINES);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(1, bucket.getComponentLines().size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id)));
        assertEquals(16, bucket.getComponentLines().get(Long.parseLong(id)).size());
    }

    @Test
    public void runRetrievesOneCharacterItemWhenItemHasFlags() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToBucket(bucket, id, "11", 0, SeparateSectionsStage.CHARACTER_CODA_LINES);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(1, bucket.getComponentLines().size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id)));
        assertEquals(16, bucket.getComponentLines().get(Long.parseLong(id)).size());
    }

    @Test
    public void runRetrievesOneProgramItem() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToBucket(bucket, id, "4", 0, SeparateSectionsStage.PROGRAM_CODA_LINES);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(1, bucket.getComponentLines().size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id)));
        assertEquals(13, bucket.getComponentLines().get(Long.parseLong(id)).size());
    }

    @Test
    public void runRetrievesOneProgramItemWhenItemHasFlags() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToBucket(bucket, id, "12", 0, SeparateSectionsStage.PROGRAM_CODA_LINES);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(1, bucket.getComponentLines().size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id)));
        assertEquals(13, bucket.getComponentLines().get(Long.parseLong(id)).size());
    }

    @Test
    public void runRetrievesOneLinkItem() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToBucketWithoutCoda(bucket, id, "2", 0);
        bucket.getSourceLines().add("0");
        addRandomLines(bucket, 1);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(1, bucket.getComponentLines().size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id)));
        assertEquals(14, bucket.getComponentLines().get(Long.parseLong(id)).size());
    }

    @Test
    public void runRetrievesOneLinkItemWithDestinations() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToBucketWithoutCoda(bucket, id, "2", 0);
        bucket.getSourceLines().add("3");
        addRandomLines(bucket, 4);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(1, bucket.getComponentLines().size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id)));
        assertEquals(17, bucket.getComponentLines().get(Long.parseLong(id)).size());
    }

    @Test
    public void runRetrievesOneLinkItemWhenItemHasFlags() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToBucketWithoutCoda(bucket, id, "10", 0);
        bucket.getSourceLines().add("0");
        addRandomLines(bucket, 1);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(1, bucket.getComponentLines().size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id)));
        assertEquals(14, bucket.getComponentLines().get(Long.parseLong(id)).size());
    }

    private void addOneDatabaseItemToBucket(ImportBucket bucket, String id, String flags, int propLines, int codaLines) {
        addOneDatabaseItemToBucketWithoutCoda(bucket, id, flags, propLines);
        addRandomLines(bucket, codaLines);
    }

    private void addOneDatabaseItemToBucketWithoutCoda(ImportBucket bucket, String id, String flags, int propLines) {
        bucket.getSourceLines().add("#" + id);
        addRandomLines(bucket, 4);
        bucket.getSourceLines().add(flags);
        addRandomLines(bucket, 4);
        bucket.getSourceLines().add("*Props*");
        addRandomLines(bucket, propLines);
        bucket.getSourceLines().add("*End*");
    }

    private void addRandomLines(ImportBucket bucket, int count) {
        for (int i = 0; i < count; i++) {
            bucket.getSourceLines().add(RandomStringUtils.randomAlphabetic(5));
        }
    }

    private ImportBucket setupBucket(String itemCount, String parameterCount) {
        ImportBucket bucket = new ImportBucket();
        List<String> lines = bucket.getSourceLines();
        lines.add("***Foxen5 TinyMUCK DUMP Format***");
        lines.add(itemCount);
        lines.add("1");
        lines.add(parameterCount);
        int count = Integer.parseInt(parameterCount);
        for (int i = 0; i < count; i++) {
            String line = RandomStringUtils.randomAlphanumeric(13);
            lines.add(line);
        }
        return bucket;
    }
}