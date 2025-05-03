package org.mumue.mumue.importer.stages;

import com.google.common.io.Resources;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.mumue.mumue.importer.ImportBucket;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

class LoadLinesStageTest {
    private final LoadLinesStage stage = new LoadLinesStage();

    @Test
    void runHandlesFileNotFoundException() throws URISyntaxException {
        ImportBucket bucket = new ImportBucket();
        bucket.setFile(RandomStringUtils.insecure().nextAlphabetic(17));

        assertThrows(RuntimeException.class, () -> stage.run(bucket));
    }

    @Test
    void runLoadsLinesFromFileAndAddsToBucket() throws URISyntaxException {
        ImportBucket bucket = new ImportBucket();
        URI uri = Resources.getResource("org/mumue/mumue/databaseimporter/LoadLinesStageTestInput.testdb").toURI();
        bucket.setFile(uri.getPath());

        stage.run(bucket);

        assertEquals("Line 1", bucket.getSourceLines().get(0));
        assertEquals("Line 2", bucket.getSourceLines().get(1));
        assertEquals("Line 3", bucket.getSourceLines().get(2));
    }

    @Test
    void runLoadsLinesWithoutTreatingCRAsDelimiter() throws URISyntaxException {
        ImportBucket bucket = new ImportBucket();
        URI uri = Resources.getResource("org/mumue/mumue/databaseimporter/LoadLinesStageTestCRInput.testdb").toURI();
        bucket.setFile(uri.getPath());

        stage.run(bucket);

        assertEquals("Line\r1", bucket.getSourceLines().get(0));
        assertEquals("Line\r2", bucket.getSourceLines().get(1));
        assertEquals("Line\r3", bucket.getSourceLines().get(2));
    }
}
