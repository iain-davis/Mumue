package org.mumue.mumue.connection.states;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.databaseimporter.DatabaseImportLauncher;
import org.mumue.mumue.databaseimporter.ImportConfiguration;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ResultOfMethodCallIgnored")
class ImportPathPromptHandlerTest {
    @TempDir
    private Path temporaryFolder;

    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final MockDatabaseImportLauncher databaseImporter = new MockDatabaseImportLauncher();
    private final TextMaker textMaker = mock(TextMaker.class);
    private final ImportPathPromptHandler importPathPromptHandler = new ImportPathPromptHandler(connectionStateProvider, databaseImporter, textMaker);

    @Test
    void returnSameStateWhenNoInput() {
        Connection connection = Nimue.connection();
        ConnectionState next = importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(next, instanceOf(ImportPathPromptHandler.class));
    }

    @Test
    void returnNextState()  {
        String fileName = RandomStringUtils.insecure().nextAlphabetic(13);
        File file = createFile(fileName);
        Connection connection = Nimue.connection();
        connection.getInputQueue().push(file.getAbsolutePath());

        ConnectionState next = importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(next, instanceOf(AdministrationMenu.class));
    }

    @Test
    void launchDatabaseImporter()  {
        when(textMaker.getText(eq(TextName.FileNotFound), eq(ConfigurationDefaults.SERVER_LOCALE), anyMap())).thenReturn("text");
        String fileName = RandomStringUtils.insecure().nextAlphabetic(13);
        File file = createFile(fileName);
        Connection connection = Nimue.connection();
        connection.getInputQueue().push(file.getAbsolutePath());

        importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(databaseImporter.importConfiguration, notNullValue());
    }

    @Test
    void launchDatabaseImporterWithFile()  {
        String fileName = RandomStringUtils.insecure().nextAlphabetic(13);
        File file = createFile(fileName);
        Connection connection = Nimue.connection();
        connection.getInputQueue().push(file.getAbsolutePath());

        importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(databaseImporter.importConfiguration.getFile(), notNullValue());
        assertThat(databaseImporter.importConfiguration.getFile().getName(), equalTo(fileName));
    }

    @Test
    void doNotLaunchWhenFileDoesNotExist()  {
        String fileName = RandomStringUtils.insecure().nextAlphabetic(13);
        Connection connection = Nimue.connection();
        connection.getInputQueue().push(fileName);
        when(textMaker.getText(eq(TextName.FileNotFound), eq(ConfigurationDefaults.SERVER_LOCALE), anyMap())).thenReturn("");

        importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(databaseImporter.importConfiguration, nullValue());
    }

    @Test
    void displayMessageWhenFileDoesNotExist() {
        String message = RandomStringUtils.insecure().nextAlphabetic(17);
        String fileName = RandomStringUtils.insecure().nextAlphabetic(13);
        Connection connection = Nimue.connection();
        connection.getInputQueue().push(fileName);
        when(textMaker.getText(eq(TextName.FileNotFound), eq(ConfigurationDefaults.SERVER_LOCALE), anyMap())).thenReturn(message);

        importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(connection.getOutputQueue(), hasItem(message));
    }

    @Test
    void rePromptWhenFileDoesNotExist() {
        String fileName = RandomStringUtils.insecure().nextAlphabetic(13);
        Connection connection = Nimue.connection();
        connection.getInputQueue().push(fileName);
        when(textMaker.getText(eq(TextName.FileNotFound), eq(ConfigurationDefaults.SERVER_LOCALE), anyMap())).thenReturn("");

        ConnectionState next = importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(next, instanceOf(ImportPathPrompt.class));
    }

    @Test
    void returnToMenuOnEmptyInput() {
        Connection connection = Nimue.connection();
        connection.getInputQueue().push("");

        ConnectionState next = importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(next, instanceOf(AdministrationMenu.class));
    }

    private File createFile(String fileName) {
        Path filePath = temporaryFolder.resolve(fileName);
        File file = filePath.toFile();
        try {
            file.createNewFile();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return file;
    }

    private static class MockDatabaseImportLauncher extends DatabaseImportLauncher {
        ImportConfiguration importConfiguration;

        private MockDatabaseImportLauncher() {
            super(null, null);
        }

        @Override
        public void launchWith(ImportConfiguration importConfiguration) {
            this.importConfiguration = importConfiguration;
        }
    }
}
