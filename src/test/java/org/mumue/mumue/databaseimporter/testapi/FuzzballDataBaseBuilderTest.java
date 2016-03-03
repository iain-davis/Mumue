package org.mumue.mumue.databaseimporter.testapi;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FuzzballDataBaseBuilderTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void writeProvidedLines() {
        List<String> expected = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            expected.add(RandomStringUtils.insecure().nextAlphanumeric(3));
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
            return IOUtils.readLines(ReaderInputStream.builder().setPath(file.getAbsolutePath()).setCharset(Charset.defaultCharset()).get(), Charset.defaultCharset());
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

    private static class MockLineBuilder implements LineBuilder {
        private final List<String> expected;

        public MockLineBuilder(List<String> expected) {
            this.expected = expected;
        }

        @Override
        public Collection<String> getLines() {
            return expected;
        }
    }
}
