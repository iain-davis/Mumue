package org.ruhlendavis.meta.importer;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

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
        lines.add(RandomStringUtils.randomNumeric(5));
        lines.add("1");
        bucket.setSourceLines(lines);
        stage.run(bucket);
        assertTrue(bucket.isFailed());
    }

    @Test
    public void runConfirmsDatabaseFormat() throws URISyntaxException {
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
    public void runRetrievesDatabaseItemCount() throws URISyntaxException {
        String parameterCount = RandomStringUtils.randomNumeric(3);
        String itemCount = RandomStringUtils.randomNumeric(5);
        ImportBucket bucket = setupBucket(itemCount, parameterCount);
        stage.run(bucket);
        assertEquals(Long.parseLong(itemCount), bucket.getDatabaseItemCount(), 0);
    }

    @Test
    public void runRetrievesParameterCount() throws URISyntaxException {
        String parameterCount = RandomStringUtils.randomNumeric(3);
        String itemCount = RandomStringUtils.randomNumeric(5);
        ImportBucket bucket = setupBucket(itemCount, parameterCount);
        stage.run(bucket);
        assertEquals(Long.parseLong(parameterCount), bucket.getParameterCount(), 0);
    }

    private ImportBucket setupBucket(String itemCount, String parameterCount) {
        ImportBucket bucket = new ImportBucket();
        List<String> lines = new ArrayList<>();
        lines.add("***Foxen5 TinyMUCK DUMP Format***");
        lines.add(itemCount);
        lines.add("1");
        lines.add(parameterCount);
        bucket.setSourceLines(lines);
        return bucket;
    }
}