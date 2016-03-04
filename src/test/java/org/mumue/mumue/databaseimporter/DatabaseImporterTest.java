package org.mumue.mumue.databaseimporter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.LocatableComponent;
import org.mumue.mumue.components.universe.UniverseBuilder;
import org.mumue.mumue.components.universe.UniverseRepository;
import org.mumue.mumue.databaseimporter.testapi.DatabaseItemLinesBuilder;
import org.mumue.mumue.databaseimporter.testapi.DatabaseLinesBuilder;
import org.mumue.mumue.databaseimporter.testapi.FuzzballDataBaseBuilder;
import org.mumue.mumue.databaseimporter.testapi.ParameterLinesBuilder;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.testobjectbuilder.Nimue;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;

public class DatabaseImporterTest {
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private static final String FUZZ_BALL_5_TINY_MUCK_FILE_FORMAT = "***Foxen5 TinyMUCK DUMP Format***";
    //    private static final Random RANDOM = new Random();
    private final UniverseRepository universeRepository = mock(UniverseRepository.class);
    private final DatabaseImporter databaseImporter = new DatabaseImporter(
            new ComponentsImporter(new GameComponentImporter()),
            new LineLoader(), new ParametersImporter(), new UniverseImporter(Nimue.componentIdManager(), new UniverseBuilder(), universeRepository));
    private final ImportConfiguration importConfiguration = new ImportConfiguration();

    @Test
    public void extractCorrectNumberOfParameterLines() {
        int parameterCount = RandomUtils.insecure().randomInt(11, 110);
        importConfiguration.setFile(createFile(parameterCount));

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getParameterCount(), equalTo(parameterCount + ParameterLinesBuilder.REQUIRED_PARAMETER_COUNT));
    }

    @Test
    public void doNothingWithUnknownFormat() {
        int itemCount = RandomUtils.insecure().randomInt(2, 3);
        long parameterCount = RandomUtils.insecure().randomInt(11, 110);
        File file = createFile(itemCount, parameterCount, RandomStringUtils.insecure().nextAlphabetic(14), "1");
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getParameterCount(), equalTo(0));
    }

    @Test
    public void doNothingWithUnknownFormatVersion() {
        int itemCount = RandomUtils.insecure().randomInt(2, 3);
        long parameterCount = RandomUtils.insecure().randomInt(11, 110);
        String formatVersion = RandomStringUtils.insecure().nextNumeric(2);
        File file = createFile(itemCount, parameterCount, FUZZ_BALL_5_TINY_MUCK_FILE_FORMAT, formatVersion);
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
        String muckName = RandomStringUtils.insecure().nextAlphabetic(13);
        File file = createFile(muckName);
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getUniverse().getName(), equalTo(muckName));
    }

    @Test
    public void setStartingLocationOnUniverse() {
        int itemCount = RandomUtils.insecure().randomInt(2, 3);
        int parameterCount = RandomUtils.insecure().randomInt(1, 10);
        long startingLocation = RandomUtils.insecure().randomInt(101, 1100);
        File file = createFile(itemCount, parameterCount, startingLocation);
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getUniverse().getStartingSpaceId(), equalTo(startingLocation));
    }

    @Test
    public void importCorrectNumberOfComponents() {
        int components = RandomUtils.insecure().randomInt(2, 5);
        int parameterCount = RandomUtils.insecure().randomInt(6, 15);
        File file = createFile(components, parameterCount);
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        assertThat(results.getComponents().size(), equalTo(components));
    }

    @Test
    public void importComponentReferenceId() {
        int components = RandomUtils.insecure().randomInt(2, 5);
        int parameterCount = RandomUtils.insecure().randomInt(6, 15);
        File file = createFile(components, parameterCount);
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        for (Component component : results.getComponents()) {
            assertThat(component.getClass().toString(), component.getId(), not(equalTo(GlobalConstants.REFERENCE_UNKNOWN)));
        }
    }

    @Test
    public void importComponentUniverseId() {
        int components = RandomUtils.insecure().randomInt(2, 5);
        int parameterCount = RandomUtils.insecure().randomInt(6, 15);
        File file = createFile(components, parameterCount);
        importConfiguration.setFile(file);

        ImportResults results = databaseImporter.importUsing(importConfiguration);

        LocatableComponent component = (LocatableComponent) results.getComponents().iterator().next();
        assertThat(component.getClass().toString(), component.getUniverseId(), not(equalTo(GlobalConstants.REFERENCE_UNKNOWN)));
    }

    private File createFile(String muckName) {
        long parameterCount = RandomUtils.insecure().randomInt(4, 13);
        return createFile(RandomUtils.insecure().randomInt(2, 3), parameterCount, muckName);
    }

    private File createFile(int parameterCount) {
        return createFile(RandomUtils.insecure().randomInt(2, 3), parameterCount);
    }

    private File createFile(int itemCount, long parameterCount) {
        return createFile(itemCount, parameterCount, RandomStringUtils.insecure().nextAlphabetic(13));
    }

    private File createFile(int itemCount, long parameterCount, long startingLocation) {
        return createFile(itemCount, parameterCount, RandomStringUtils.insecure().nextAlphabetic(13), startingLocation);
    }

    private File createFile(int itemCount, long parameterCount, String muckName) {
        return createFile(itemCount, parameterCount, muckName, RandomUtils.insecure().randomInt(1, 1000));
    }

    private File createFile(int itemCount, long parameterCount, String muckName, long startingLocation) {
        return createFile(itemCount, parameterCount, FUZZ_BALL_5_TINY_MUCK_FILE_FORMAT, "1", muckName, startingLocation);
    }

    private File createFile(int itemCount, long parameterCount, String fileFormat, String formatVersion) {
        return createFile(itemCount, parameterCount, fileFormat, formatVersion, RandomStringUtils.insecure().nextAlphabetic(13), RandomUtils.insecure().randomInt(1, 1000));
    }

    private File createFile(Integer itemCount, Long parameterCount, String fileFormat, String formatVersion, String muckName, long startingLocation) {
        ParameterLinesBuilder parameterLinesBuilder = new ParameterLinesBuilder()
                .withAdditionalRandomParameters(parameterCount)
                .withMuckName(muckName)
                .withPlayerStart(startingLocation);

        DatabaseLinesBuilder databaseLinesBuilder = new DatabaseLinesBuilder(parameterLinesBuilder, new DatabaseItemLinesBuilder())
                .withDatabaseItemCount(itemCount)
                .withFormat(fileFormat)
                .withFormatVersion(formatVersion);

        return new FuzzballDataBaseBuilder(temporaryFolder, databaseLinesBuilder).

                build();
    }
}
