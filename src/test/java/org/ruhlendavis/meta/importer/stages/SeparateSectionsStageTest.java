package org.ruhlendavis.meta.importer.stages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import org.ruhlendavis.meta.importer.ImportBucket;
import org.ruhlendavis.meta.importer.ImporterStageTestHelper;

public class SeparateSectionsStageTest extends ImporterStageTestHelper {
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
        addOneDatabaseItemToList(bucket.getSourceLines(), "#1", "0", 0, 3);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(Long.parseLong(itemCount), bucket.getDatabaseItemCount(), 0);
    }

    @Test
    public void runRetrievesParameterCount() {
        String parameterCount = RandomStringUtils.randomNumeric(2);
        String itemCount = RandomStringUtils.randomNumeric(2);
        ImportBucket bucket = setupBucket(itemCount, parameterCount);
        addOneDatabaseItemToList(bucket.getSourceLines(), "#1", "0", 0, 3);
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
        addOneDatabaseItemToList(bucket.getSourceLines(), "#1", "0", 0, 3);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(count, bucket.getParameterCount(), 0);
        assertEquals(count, bucket.getParameterLines().size(), 0);
    }

    @Test
    public void runClearsSourceLinesWhenFinished() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToList(bucket.getSourceLines(), id, "0", 0, SeparateSectionsStage.SPACE_CODA_LINES);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(0, bucket.getSourceLines().size());
    }

    @Test
    public void runIgnoresGarbageItem() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToList(bucket.getSourceLines(), id, "6", 0, 0);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(0, bucket.getComponentLines().size());
        assertNull(bucket.getComponentLines().get(Long.parseLong(id)));
    }

    @Test
    public void runRetrievesOneSpaceItem() {
        ImportBucket bucket = setupBucket("1", "1");
        String id = RandomStringUtils.randomNumeric(3);
        addOneDatabaseItemToList(bucket.getSourceLines(), id, "0", 0, SeparateSectionsStage.SPACE_CODA_LINES);
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
        addOneDatabaseItemToList(bucket.getSourceLines(), id, "8", 0, SeparateSectionsStage.SPACE_CODA_LINES);
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
        addOneDatabaseItemToList(bucket.getSourceLines(), id1, "0", 0, SeparateSectionsStage.SPACE_CODA_LINES);
        addOneDatabaseItemToList(bucket.getSourceLines(), id2, "0", 1, SeparateSectionsStage.SPACE_CODA_LINES);
        addOneDatabaseItemToList(bucket.getSourceLines(), id3, "0", 2, SeparateSectionsStage.SPACE_CODA_LINES);
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
        addOneDatabaseItemToList(bucket.getSourceLines(), id, "1", 0, SeparateSectionsStage.ARTIFACT_CODA_LINES);
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
        addOneDatabaseItemToList(bucket.getSourceLines(), id, "9", 0, SeparateSectionsStage.ARTIFACT_CODA_LINES);
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
        addOneDatabaseItemToList(bucket.getSourceLines(), id, "3", 0, SeparateSectionsStage.CHARACTER_CODA_LINES);
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
        addOneDatabaseItemToList(bucket.getSourceLines(), id, "11", 0, SeparateSectionsStage.CHARACTER_CODA_LINES);
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
        addOneDatabaseItemToList(bucket.getSourceLines(), id, "4", 0, SeparateSectionsStage.PROGRAM_CODA_LINES);
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
        addOneDatabaseItemToList(bucket.getSourceLines(), id, "12", 0, SeparateSectionsStage.PROGRAM_CODA_LINES);
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
        addOneDatabaseItemToList(bucket.getSourceLines(), id, "2", 0);
        bucket.getSourceLines().add("0");
        addRandomLinesToList(bucket.getSourceLines(), 1);
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
        addOneDatabaseItemToList(bucket.getSourceLines(), id, "2", 0);
        bucket.getSourceLines().add("3");
        addRandomLinesToList(bucket.getSourceLines(), 4);
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
        addOneDatabaseItemToList(bucket.getSourceLines(), id, "10", 0);
        bucket.getSourceLines().add("0");
        addRandomLinesToList(bucket.getSourceLines(), 1);
        bucket.getSourceLines().add("***END OF DUMP***");
        stage.run(bucket);
        assertEquals(1, bucket.getComponentLines().size());
        assertNotNull(bucket.getComponentLines().get(Long.parseLong(id)));
        assertEquals(14, bucket.getComponentLines().get(Long.parseLong(id)).size());
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