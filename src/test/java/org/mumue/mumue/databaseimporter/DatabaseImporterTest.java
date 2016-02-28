package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
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
import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.LocatableComponent;
import org.mumue.mumue.components.universe.UniverseBuilder;
import org.mumue.mumue.components.universe.UniverseRepository;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.testobjectbuilder.Nimue;

public class DatabaseImporterTest {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private static final String FUZZ_BALL_5_TINY_MUCK_FILE_FORMAT = "***Foxen5 TinyMUCK DUMP Format***";
    private static final Random RANDOM = new Random();
    private final UniverseRepository universeRepository = mock(UniverseRepository.class);
    private final DatabaseImporter databaseImporter = new DatabaseImporter(
            new ComponentsImporter(new GameComponentImporter()),
            new LineLoader(), new ParametersExtractor(), new UniverseImporter(Nimue.componentIdManager(), new UniverseBuilder(), universeRepository));
    private final ImportConfiguration importConfiguration = new ImportConfiguration();

    @Test
    public void extractCorrectNumberOfParameterLines() {
        int parameterCount = RANDOM.nextInt(100) + 10;
        importConfiguration.setFile(createFile(parameterCount));

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getParameterCount(), equalTo(parameterCount + 2));
    }

    @Test
    public void doNothingWithUnknownFormat() {
        long parameterCount = RANDOM.nextInt(100) + 10;
        File file = createFile(RANDOM.nextInt(2) + 1, parameterCount, RandomStringUtils.randomAlphabetic(14), "1");
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getParameterCount(), equalTo(0));
    }

    @Test
    public void doNothingWithUnknownFormatVersion() {
        long parameterCount = RANDOM.nextInt(100) + 10;
        File file = createFile(RANDOM.nextInt(2) + 1, parameterCount, FUZZ_BALL_5_TINY_MUCK_FILE_FORMAT, RandomStringUtils.randomNumeric(2));
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getParameterCount(), equalTo(0));
    }

    @Test
    public void doNothingWithEmptyFile() throws IOException {
        File file = temporaryFolder.newFile();
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getComponentCount(), equalTo(0));
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
        File file = createFile(RANDOM.nextInt(2) + 1, RANDOM.nextInt(10), startingLocation);
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getUniverse().getStartingSpaceId(), equalTo(startingLocation));
    }

    @Test
    public void importCorrectNumberOfComponents() {
        int components = RANDOM.nextInt(4) + 1;
        File file = createFile(components, RANDOM.nextInt(10) + 5);
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getComponents().size(), equalTo(components));
    }

    @Test
    public void importComponentReferenceId() {
        int components = RANDOM.nextInt(4) + 1;
        File file = createFile(components, RANDOM.nextInt(10) + 5);
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        for (Component component : results.getComponents()) {
            assertThat(component.getClass().toString(), component.getId(), not(equalTo(GlobalConstants.REFERENCE_UNKNOWN)));
        }
    }

    @Test
    public void importComponentUniverseId() {
        int components = RANDOM.nextInt(4) + 1;
        File file = createFile(components, RANDOM.nextInt(10) + 5);
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        LocatableComponent component = (LocatableComponent) results.getComponents().iterator().next();
        assertThat(component.getClass().toString(), component.getUniverseId(), not(equalTo(GlobalConstants.REFERENCE_UNKNOWN)));
    }

    private File createFile(String muckName) {
        long parameterCount = RANDOM.nextInt(10) + 3;
        return createFile(RANDOM.nextInt(2) + 1, parameterCount, muckName);
    }

    private File createFile(int parameterCount) {
        return createFile(RANDOM.nextInt(2) + 1, parameterCount);
    }

    private File createFile(int itemCount, long parameterCount) {
        return createFile(itemCount, parameterCount, RandomStringUtils.randomAlphabetic(13));
    }

    private File createFile(int itemCount, long parameterCount, long startingLocation) {
        return createFile(itemCount, parameterCount, RandomStringUtils.randomAlphabetic(13), startingLocation);
    }

    private File createFile(int itemCount, long parameterCount, String muckName) {
        return createFile(itemCount, parameterCount, muckName, RANDOM.nextInt(1000));
    }

    private File createFile(int itemCount, long parameterCount, String muckName, long startingLocation) {
        return createFile(itemCount, parameterCount, FUZZ_BALL_5_TINY_MUCK_FILE_FORMAT, "1", muckName, startingLocation);
    }

    private File createFile(int itemCount, long parameterCount, String fileFormat, String formatVersion) {
        return createFile(itemCount, parameterCount, fileFormat, formatVersion, RandomStringUtils.randomAlphabetic(13), RANDOM.nextInt(1000));
    }

    private File createFile(Integer itemCount, Long parameterCount, String fileFormat, String formatVersion, String muckName, long startingLocation) {
        File file = createTemporaryFile(muckName);
        PrintWriter writer = createWriter(file);
        writer.println(fileFormat);
        writer.println(itemCount.toString());
        writer.println(formatVersion);
        List<String> lines = ImportTestHelper.generateLines(parameterCount, muckName, startingLocation, itemCount);
        writer.println(String.valueOf(parameterCount + 2));
        lines.stream().forEach(writer::println);
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