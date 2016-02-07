package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mumue.mumue.components.universe.UniverseRepository;

public class DatabaseImporterTest {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private static final String FUZZ_BALL_5_TINY_MUCK_FILE_FORMAT = "***Foxen5 TinyMUCK DUMP Format***";
    private static final Random RANDOM = new Random();
    private final UniverseRepository universeRepository = mock(UniverseRepository.class);
    private final DatabaseImporter databaseImporter = new DatabaseImporter(new ComponentCountExtractor(), new LineLoader(), new ParametersExtractor(), new UniverseImporter(universeRepository));
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

    @Test
    public void createUniverseFromMuckName() {
        String muckName = RandomStringUtils.randomAlphabetic(13);
        File file = createFile(muckName);
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getUniverse().getName(), equalTo(muckName));
    }

    @Test
    public void setStartingLocationOnUniverse() {
        long startingLocation = RANDOM.nextInt(1000) + 100;
        File file = createFile(0, RANDOM.nextInt(10), startingLocation);
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getUniverse().getStartingSpaceId(), equalTo(startingLocation));
    }

    private File createFile(String muckName) {
        long parameterCount = RANDOM.nextInt(10);
        return createFile(0L, parameterCount, muckName);
    }

    private File createFile(long parameterCount) {
        return createFile(0L, parameterCount);
    }

    private File createFile(long itemCount, long parameterCount) {
        return createFile(itemCount, parameterCount, RandomStringUtils.randomAlphabetic(13));
    }

    private File createFile(long itemCount, long parameterCount, long startingLocation) {
        return createFile(itemCount, parameterCount, RandomStringUtils.randomAlphabetic(13), startingLocation);
    }

    private File createFile(long itemCount, long parameterCount, String muckName) {
        return createFile(itemCount, parameterCount, muckName, RANDOM.nextInt(1000));
    }

    private File createFile(long itemCount, long parameterCount, String muckName, long startingLocation) {
        return createFile(itemCount, parameterCount, FUZZ_BALL_5_TINY_MUCK_FILE_FORMAT, "1", muckName, startingLocation);
    }

    private File createFile(long itemCount, long parameterCount, String fileFormat, String formatVersion) {
        return createFile(itemCount, parameterCount, fileFormat, formatVersion, RandomStringUtils.randomAlphabetic(13), RANDOM.nextInt(1000));
    }

    private File createFile(Long itemCount, Long parameterCount, String fileFormat, String formatVersion, String muckName, long startingLocation) {
        parameterCount = parameterCount - 2;
        File file = createTemporaryFile(muckName);
        PrintWriter writer = createWriter(file);
        writer.println(fileFormat);
        writer.println(itemCount.toString());
        writer.println(formatVersion);
        List<String> parameterLines = ImportTestHelper.generateParameterLines(parameterCount, muckName, startingLocation);
        writer.println(String.valueOf(parameterLines.size()));
        parameterLines.stream().forEach(writer::println);
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

    private File createTemporaryFile(String fileName) {
        try {
            return temporaryFolder.newFile(fileName);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}