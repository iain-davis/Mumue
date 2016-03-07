package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Random;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.LocatableComponent;
import org.mumue.mumue.components.universe.Universe;
import org.mumue.mumue.components.universe.UniverseRepository;
import org.mumue.mumue.database.DatabaseAccessor;
import org.mumue.mumue.database.DatabaseHelper;
import org.mumue.mumue.database.TestDatabaseModule;
import org.mumue.mumue.databaseimporter.testapi.DatabaseItemLinesBuilder;
import org.mumue.mumue.databaseimporter.testapi.DatabaseLinesBuilder;
import org.mumue.mumue.databaseimporter.testapi.FuzzballDataBaseBuilder;
import org.mumue.mumue.databaseimporter.testapi.ParameterLinesBuilder;
import org.mumue.mumue.importer.GlobalConstants;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;

public class DatabaseImporterTest {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();

    private final DatabaseAccessor database = DatabaseHelper.setupTestDatabaseWithSchema();
    private final Injector injector = Guice.createInjector(new TestDatabaseModule(database));

    private final ParameterLinesBuilder parameterLinesBuilder = new ParameterLinesBuilder();
    private final DatabaseLinesBuilder databaseLinesBuilder = new DatabaseLinesBuilder(parameterLinesBuilder, new DatabaseItemLinesBuilder());
    private final FuzzballDataBaseBuilder fuzzballDataBaseBuilder = new FuzzballDataBaseBuilder(temporaryFolder, databaseLinesBuilder);

    private final DatabaseImporter importer = injector.getInstance(DatabaseImporter.class);

    @Test
    public void addANewUniverseForAllTheNewComponents() {
        ImportConfiguration configuration = new ImportConfiguration();
        configuration.setFile(fuzzballDataBaseBuilder.build());

        importer.importUsing(configuration);

        UniverseRepository universeRepository = new UniverseRepository(database);

        assertThat(universeRepository.getUniverses().size(), equalTo(1));
    }

    @Test
    public void newUniverseHasImportedName() {
        String name = RandomStringUtils.randomAlphanumeric(25);
        parameterLinesBuilder.withMuckName(name);
        ImportConfiguration configuration = new ImportConfiguration();
        configuration.setFile(fuzzballDataBaseBuilder.build());

        importer.importUsing(configuration);

        UniverseRepository universeRepository = new UniverseRepository(database);
        Collection<Universe> universes = universeRepository.getUniverses();
        boolean hasUniverse = universes.stream().anyMatch(universe -> universe.getName().equals(name));

        assertThat(hasUniverse, equalTo(true));
    }

    @Test
    public void newUniverseHasPlayerStart() {
        long start = new Random().nextInt(1000) + 1;
        parameterLinesBuilder.withPlayerStart(start);
        ImportConfiguration configuration = new ImportConfiguration();
        configuration.setFile(fuzzballDataBaseBuilder.build());

        importer.importUsing(configuration);

        UniverseRepository universeRepository = new UniverseRepository(database);
        Collection<Universe> universes = universeRepository.getUniverses();
        boolean hasUniverse = universes.stream().anyMatch(universe -> universe.getStartingSpaceId() == start);

        assertThat(hasUniverse, equalTo(true));
    }

    @Test
    public void doNothingWithUnknownFormat() {
        databaseLinesBuilder.withFormat(RandomStringUtils.randomAlphanumeric(25));
        ImportConfiguration configuration = new ImportConfiguration();
        configuration.setFile(fuzzballDataBaseBuilder.build());

        importer.importUsing(configuration);

        UniverseRepository universeRepository = new UniverseRepository(database);
        assertThat(universeRepository.getUniverses().size(), equalTo(0));
    }

    @Test
    public void doNothingWithUnknownFormatVersion() {
        databaseLinesBuilder.withFormatVersion(RandomStringUtils.randomNumeric(25));
        ImportConfiguration configuration = new ImportConfiguration();
        configuration.setFile(fuzzballDataBaseBuilder.build());

        importer.importUsing(configuration);

        UniverseRepository universeRepository = new UniverseRepository(database);
        assertThat(universeRepository.getUniverses().size(), equalTo(0));
    }

    @Test
    public void doNothingWithEmptyFile() throws IOException {
        ImportConfiguration configuration = new ImportConfiguration();
        configuration.setFile(temporaryFolder.newFile());

        importer.importUsing(configuration);

        UniverseRepository universeRepository = new UniverseRepository(database);
        assertThat(universeRepository.getUniverses().size(), equalTo(0));
    }
//
//    @Test
//    public void importCorrectNumberOfComponents() {
//        int components = new Random().nextInt(4) + 1;
//        File file = createFile(components, new Random().nextInt(10) + 5);
//        importConfiguration.setFile(file);
//
//        ImportResults results = databaseImporter.importUsing(importConfiguration);
//
//        assertThat(results.getComponents().size(), equalTo(components));
//    }
//
//    @Test
//    public void importComponentReferenceId() {
//        int components = new Random().nextInt(4) + 1;
//        File file = createFile(components, new Random().nextInt(10) + 5);
//        importConfiguration.setFile(file);
//
//        ImportResults results = databaseImporter.importUsing(importConfiguration);
//
//        for (Component component : results.getComponents()) {
//            assertThat(component.getClass().toString(), component.getId(), not(equalTo(GlobalConstants.REFERENCE_UNKNOWN)));
//        }
//    }
//
//    @Test
//    public void importComponentUniverseId() {
//        int components = new Random().nextInt(4) + 1;
//        File file = createFile(components, new Random().nextInt(10) + 5);
//        importConfiguration.setFile(file);
//
//        ImportResults results = databaseImporter.importUsing(importConfiguration);
//
//        LocatableComponent component = (LocatableComponent) results.getComponents().iterator().next();
//        assertThat(component.getClass().toString(), component.getUniverseId(), not(equalTo(GlobalConstants.REFERENCE_UNKNOWN)));
//    }

}
