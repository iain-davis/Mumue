package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DatabaseImporterTest {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private static final String FUZZ_BALL_5_TINY_MUCK_FILE_FORMAT = "***Foxen5 TinyMUCK DUMP Format***";
    private static final Random RANDOM = new Random();
    private final DatabaseImporter databaseImporter = new DatabaseImporter(new ComponentCountExtractor(), new LineLoader(), new ParametersExtractor());
    private final ImportConfiguration importConfiguration = new ImportConfiguration();

    @Test
    public void extractCorrectNumberOfParameterLines() {
        long parameterCount = RANDOM.nextInt(100) + 10;
        importConfiguration.setFile(createFile(parameterCount));

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getParameterCount(), equalTo(parameterCount));
    }

    @Test
    public void doNothingWithUnknownFormat() {
        long parameterCount = RANDOM.nextInt(100) + 10;
        File file = createFile(0L, parameterCount, RandomStringUtils.randomAlphabetic(14), "1");
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getParameterCount(), equalTo(0L));
    }

    @Test
    public void doNothingWithUnknownFormatVersion() {
        long parameterCount = RANDOM.nextInt(100) + 10;
        File file = createFile(0L, parameterCount, FUZZ_BALL_5_TINY_MUCK_FILE_FORMAT, RandomStringUtils.randomNumeric(2));
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getParameterCount(), equalTo(0L));
    }

    @Test
    public void doNothingWithEmptyFile() throws IOException {
        File file = temporaryFolder.newFile();
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getComponentCount(), equalTo(0L));
    }

    private File createFile(long parameterCount) {
        return createFile(0, parameterCount);
    }

    private File createFile(long itemCount, long parameterCount) {
        return createFile(itemCount, parameterCount, FUZZ_BALL_5_TINY_MUCK_FILE_FORMAT, "1");
    }

    private File createFile(Long itemCount, Long parameterCount, String fileFormat, String formatVersion) {
        File file = createTemporaryFile();
        PrintWriter writer = createWriter(file);
        writer.println(fileFormat);
        writer.println(itemCount.toString());
        writer.println(formatVersion);
        writer.println(parameterCount.toString());
        ImportTestHelper.generateParameterLines(parameterCount).stream().forEach(writer::println);
        writer.flush();
        writer.close();
        return file;
    }

    private PrintWriter createWriter(File file) {
        try {
            return new PrintWriter(file) {
                @Override
                public void println(String line) {
                    print(line + "\n");
                }
            };
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    private File createTemporaryFile() {
        try {
            return temporaryFolder.newFile();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}