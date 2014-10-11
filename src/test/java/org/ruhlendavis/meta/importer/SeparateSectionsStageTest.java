package org.ruhlendavis.meta.importer;

import com.google.common.io.Resources;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SeparateSectionsStageTest {
    SeparateSectionsStage stage = new SeparateSectionsStage();

    @Test
    public void runHandlesNoLines() {
        ImportBucket bucket = new ImportBucket();
        bucket.setSourceLines(new ArrayList<>());
        stage.run(bucket);
        assertTrue(bucket.isFailed());
    }

    @Test
    public void runConfirmsDatabaseVersion() throws URISyntaxException {
        ImportBucket bucket = new ImportBucket();
        List<String> lines = new ArrayList<>();
        lines.add(RandomStringUtils.randomAlphabetic(25));
        bucket.setSourceLines(lines);
        stage.run(bucket);
        assertTrue(bucket.isFailed());
    }

    @Test
    public void runRetrievesDatabaseItemCount() throws URISyntaxException {
        ImportBucket bucket = new ImportBucket();
        List<String> lines = new ArrayList<>();
        lines.add("***Foxen5 TinyMUCK DUMP Format***");
        String count = RandomStringUtils.randomNumeric(5);
        lines.add(count);
        bucket.setSourceLines(lines);
        stage.run(bucket);
        assertEquals(Long.parseLong(count), bucket.getDatabaseItemCount(), 0);
    }
}
//    @Test
//    public void runRejectsCompressedFile() throws URISyntaxException {
//        ImportBucket bucket = new ImportBucket();
//        URI uri = Resources.getResource("org/ruhlendavis/meta/importer/SeparateSectionsStageTestCompressedInput.db").toURI();
//        bucket.setFile(uri.getPath());
//        stage.run(bucket);
//        assertEquals(0, bucket.getComponentLines().size());
//    }
//    @Test
//    public void runHandlesTestInputFile() throws URISyntaxException {
//        ImportBucket bucket = new ImportBucket();
//        URI uri = Resources.getResource("org/ruhlendavis/meta/importer/SeparateSectionsStageTestInput.db").toURI();
//        bucket.setFile(uri.getPath());
//        stage.run(bucket);
//        assertEquals(300, bucket.getParameterLines().size());
//        assertEquals(7, bucket.getComponentLines().size());
