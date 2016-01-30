package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;
import org.mumue.mumue.importer.ImportBucket;

public class LineLoaderTest {
    @Rule public ExpectedException thrown = ExpectedException.none();
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void runLoadsLinesFromFileAndAddsToBucket() throws URISyntaxException {
        LineLoader lineLoader = new LineLoader();
        URI uri = Resources.getResource("org/mumue/mumue/databaseimporter/LoadLinesStageTestInput.db").toURI();
        File file = FileUtils.getFile(uri.getPath());

        List<String> lines = lineLoader.loadFrom(file);

        assertThat(lines, contains("Line 1", "Line 2", "Line 3"));
    }

    @Test
    public void runLoadsLinesWithoutTreatingCRAsDelimiter() throws URISyntaxException {
        LineLoader lineLoader = new LineLoader();
        URI uri = Resources.getResource("org/mumue/mumue/databaseimporter/LoadLinesStageTestCRInput.db").toURI();
        File file = FileUtils.getFile(uri.getPath());

        List<String> lines = lineLoader.loadFrom(file);

        assertThat(lines, contains("Line\r1", "Line\r2", "Line\r3"));
    }

    @Test
    public void neverReturnNull() throws IOException {
        File file = temporaryFolder.newFile();
        LineLoader lineLoader = new LineLoader();

        List<String> lines = lineLoader.loadFrom(file);

        assertThat(lines, notNullValue());
        assertThat(lines, is(empty()));
    }

    @Test
    public void handleFileNotFound() {
        File file = new File(RandomStringUtils.randomAlphabetic(13));
        thrown.expect(RuntimeException.class);
        thrown.expectCause(instanceOf(FileNotFoundException.class));

        new LineLoader().loadFrom(file);
    }
}