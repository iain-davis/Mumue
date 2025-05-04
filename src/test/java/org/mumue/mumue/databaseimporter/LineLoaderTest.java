package org.mumue.mumue.databaseimporter;

import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThrows;

class LineLoaderTest {
    @Test
    void runLoadsLinesFromFileAndAddsToBucket() throws URISyntaxException {
        LineLoader lineLoader = new LineLoader();
        URI uri = Resources.getResource("org/mumue/mumue/databaseimporter/LoadLinesStageTestInput.testdb").toURI();
        File file = FileUtils.getFile(uri.getPath());

        List<String> lines = lineLoader.loadFrom(file);

        assertThat(lines, contains("Line 1", "Line 2", "Line 3"));
    }

    @Test
    void runLoadsLinesWithoutTreatingCRAsDelimiter() throws URISyntaxException {
        LineLoader lineLoader = new LineLoader();
        URI uri = Resources.getResource("org/mumue/mumue/databaseimporter/LoadLinesStageTestCRInput.testdb").toURI();
        File file = FileUtils.getFile(uri.getPath());

        List<String> lines = lineLoader.loadFrom(file);

        assertThat(lines, contains("Line\r1", "Line\r2", "Line\r3"));
    }

    @Test
    void handleFileNotFound() {
        File file = new File(RandomStringUtils.insecure().nextAlphabetic(13));

        Exception exception = assertThrows(RuntimeException.class, () -> new LineLoader().loadFrom(file));

        assertThat(exception.getCause(), instanceOf(FileNotFoundException.class));
    }
}
