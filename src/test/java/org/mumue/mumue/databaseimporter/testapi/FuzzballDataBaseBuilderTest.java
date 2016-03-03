package org.mumue.mumue.databaseimporter.testapi;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FuzzballDataBaseBuilderTest {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void writeProvidedLines() {
        List<String> expected = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            expected.add(RandomStringUtils.randomAlphanumeric(3));
        }
        MockLineBuilder lineBuilder = new MockLineBuilder(expected);
        File file = new FuzzballDataBaseBuilder(temporaryFolder, lineBuilder).build();

        List<String> lines = getLinesFrom(file);

        assertThat(lines.size(), equalTo(expected.size()));
        for (int i = 0; i < expected.size(); i++) {
            assertThat(lines.get(i), equalTo(expected.get(i)));
        }
    }

    private List<String> getLinesFrom(File file) {
        try {
            return IOUtils.readLines(new ReaderInputStream(createReader(file)));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private FileReader createReader(File file) {
        try {
            return new FileReader(file);
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    private class MockLineBuilder implements LineBuilder {
        private final List<String> expected;

        public MockLineBuilder(List<String> expected) {
            this.expected = expected;
        }

        @Override
        public List<String> getLines() {
            return expected;
        }
    }
}