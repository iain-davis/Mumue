package org.ruhlendavis.meta.importer.stages;

import com.google.common.io.Resources;
import org.junit.Test;
import org.ruhlendavis.meta.importer.ImportBucket;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class LoadLinesStageTest {
    LoadLinesStage stage = new LoadLinesStage();

    @Test
    public void runLoadsLinesFromFileAndAddsToBucket() throws URISyntaxException {
        ImportBucket bucket = new ImportBucket();
        URI uri = Resources.getResource("org/ruhlendavis/meta/importer/stages/LoadLinesStageTestInput.db").toURI();
        bucket.setFile(uri.getPath());

        stage.run(bucket);

        assertEquals("Line 1", bucket.getSourceLines().get(0));
        assertEquals("Line 2", bucket.getSourceLines().get(1));
        assertEquals("Line 3", bucket.getSourceLines().get(2));
    }

    @Test
    public void runLoadsLinesWithoutTreatingCRAsDelimiter() throws URISyntaxException {
        ImportBucket bucket = new ImportBucket();
        URI uri = Resources.getResource("org/ruhlendavis/meta/importer/stages/LoadLinesStageTestCRInput.db").toURI();
        bucket.setFile(uri.getPath());

        stage.run(bucket);

        assertEquals("Line\r1", bucket.getSourceLines().get(0));
        assertEquals("Line\r2", bucket.getSourceLines().get(1));
        assertEquals("Line\r3", bucket.getSourceLines().get(2));
    }
}
