package org.ruhlendavis.mumue.importer.stages;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import com.google.common.io.Resources;
import org.junit.Test;

import org.ruhlendavis.mumue.importer.ImportBucket;

public class LoadLinesStageTest {
    LoadLinesStage stage = new LoadLinesStage();

    @Test
    public void runLoadsLinesFromFileAndAddsToBucket() throws URISyntaxException {
        ImportBucket bucket = new ImportBucket();
        URI uri = Resources.getResource("org/ruhlendavis/mumue/importer/stages/LoadLinesStageTestInput.db").toURI();
        bucket.setFile(uri.getPath());

        stage.run(bucket);

        assertEquals("Line 1", bucket.getSourceLines().get(0));
        assertEquals("Line 2", bucket.getSourceLines().get(1));
        assertEquals("Line 3", bucket.getSourceLines().get(2));
    }

    @Test
    public void runLoadsLinesWithoutTreatingCRAsDelimiter() throws URISyntaxException {
        ImportBucket bucket = new ImportBucket();
        URI uri = Resources.getResource("org/ruhlendavis/mumue/importer/stages/LoadLinesStageTestCRInput.db").toURI();
        bucket.setFile(uri.getPath());

        stage.run(bucket);

        assertEquals("Line\r1", bucket.getSourceLines().get(0));
        assertEquals("Line\r2", bucket.getSourceLines().get(1));
        assertEquals("Line\r3", bucket.getSourceLines().get(2));
    }
}
