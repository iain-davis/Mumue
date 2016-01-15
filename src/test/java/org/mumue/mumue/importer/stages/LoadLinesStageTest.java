package org.mumue.mumue.importer.stages;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import com.google.common.io.Resources;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.mumue.mumue.importer.ImportBucket;

public class LoadLinesStageTest {
    @Rule public ExpectedException thrown = ExpectedException.none();
    private final LoadLinesStage stage = new LoadLinesStage();

    @Test
    public void runHandlesFileNotFoundException() throws URISyntaxException {
        ImportBucket bucket = new ImportBucket();
        bucket.setFile(RandomStringUtils.randomAlphabetic(17));

        thrown.expect(RuntimeException.class);

        stage.run(bucket);
    }

    @Test
    public void runLoadsLinesFromFileAndAddsToBucket() throws URISyntaxException {
        ImportBucket bucket = new ImportBucket();
        URI uri = Resources.getResource("org/mumue/mumue/importer/stages/LoadLinesStageTestInput.db").toURI();
        bucket.setFile(uri.getPath());

        stage.run(bucket);

        assertEquals("Line 1", bucket.getSourceLines().get(0));
        assertEquals("Line 2", bucket.getSourceLines().get(1));
        assertEquals("Line 3", bucket.getSourceLines().get(2));
    }

    @Test
    public void runLoadsLinesWithoutTreatingCRAsDelimiter() throws URISyntaxException {
        ImportBucket bucket = new ImportBucket();
        URI uri = Resources.getResource("org/mumue/mumue/importer/stages/LoadLinesStageTestCRInput.db").toURI();
        bucket.setFile(uri.getPath());

        stage.run(bucket);

        assertEquals("Line\r1", bucket.getSourceLines().get(0));
        assertEquals("Line\r2", bucket.getSourceLines().get(1));
        assertEquals("Line\r3", bucket.getSourceLines().get(2));
    }
}
