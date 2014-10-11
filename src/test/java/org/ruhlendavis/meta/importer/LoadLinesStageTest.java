package org.ruhlendavis.meta.importer;

import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class LoadLinesStageTest {
    LoadLinesStage stage = new LoadLinesStage();

    @Test
    public void runLoadsLinesFromFileAndAddsToBucket() throws URISyntaxException {
        ImportBucket bucket = new ImportBucket();
        URI uri = Resources.getResource("org/ruhlendavis/meta/importer/LoadLinesStageTest.txt").toURI();
        bucket.setFile(uri.getPath());

        stage.run(bucket);

        assertEquals("Line 1", bucket.getSourceLines().get(0));
        assertEquals("Line 2", bucket.getSourceLines().get(1));
        assertEquals("Line 3", bucket.getSourceLines().get(2));
    }
}
