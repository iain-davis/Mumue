package org.mumue.mumue.connection.states;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mumue.mumue.configuration.ConfigurationDefaults;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.databaseimporter.DatabaseImporter;
import org.mumue.mumue.databaseimporter.ImportConfiguration;
import org.mumue.mumue.testobjectbuilder.Nimue;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

public class ImportPathPromptHandlerTest {
    @Rule public TemporaryFolder temporaryFolder = new TemporaryFolder();
    private final ConnectionStateProvider connectionStateProvider = Nimue.stateProvider();
    private final MockDatabaseImporter databaseImporter = new MockDatabaseImporter();
    private final TextMaker textMaker = mock(TextMaker.class);
    private final ImportPathPromptHandler importPathPromptHandler = new ImportPathPromptHandler(connectionStateProvider, databaseImporter, textMaker);

    @Test
    public void returnSameStateWhenNoInput() {
        Connection connection = Nimue.connection();
        ConnectionState next = importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(next, instanceOf(ImportPathPromptHandler.class));
    }

    @Test
    public void returnNextState() throws IOException {
        String fileName = RandomStringUtils.randomAlphabetic(13);
        File file = temporaryFolder.newFile(fileName);
        Connection connection = Nimue.connection();
        connection.getInputQueue().push(file.getAbsolutePath());

        ConnectionState next = importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(next, instanceOf(AdministrationMenu.class));
    }

    @Test
    public void launchDatabaseImporter() throws IOException {
        String fileName = RandomStringUtils.randomAlphabetic(13);
        File file = temporaryFolder.newFile(fileName);
        Connection connection = Nimue.connection();
        connection.getInputQueue().push(file.getAbsolutePath());

        importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(databaseImporter.importConfiguration, notNullValue());
    }

    @Test
    public void launchDatabaseImporterWithFile() throws IOException {
        String fileName = RandomStringUtils.randomAlphabetic(13);
        File file = temporaryFolder.newFile(fileName);
        Connection connection = Nimue.connection();
        connection.getInputQueue().push(file.getAbsolutePath());

        importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(databaseImporter.importConfiguration.getFile(), notNullValue());
        assertThat(databaseImporter.importConfiguration.getFile().getName(), equalTo(fileName));
    }

    @Test
    public void doNotLaunchWhenFileDoesNotExist() throws IOException {
        String fileName = RandomStringUtils.randomAlphabetic(13);
        Connection connection = Nimue.connection();
        connection.getInputQueue().push(fileName);
        when(textMaker.getText(eq(TextName.FileNotFound), eq(ConfigurationDefaults.SERVER_LOCALE), anyMapOf(String.class, String.class))).thenReturn("");

        importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(databaseImporter.importConfiguration, nullValue());
    }

    @Test
    public void displayMessageWhenFileDoesNotExist() {
        String message = RandomStringUtils.randomAlphabetic(17);
        String fileName = RandomStringUtils.randomAlphabetic(13);
        Connection connection = Nimue.connection();
        connection.getInputQueue().push(fileName);
        when(textMaker.getText(eq(TextName.FileNotFound), eq(ConfigurationDefaults.SERVER_LOCALE), anyMapOf(String.class, String.class))).thenReturn(message);

        importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(connection.getOutputQueue(), hasItem(message));
    }

    @Test
    public void rePromptWhenFileDoesNotExist() {
        String fileName = RandomStringUtils.randomAlphabetic(13);
        Connection connection = Nimue.connection();
        connection.getInputQueue().push(fileName);
        when(textMaker.getText(eq(TextName.FileNotFound), eq(ConfigurationDefaults.SERVER_LOCALE), anyMapOf(String.class, String.class))).thenReturn("");

        ConnectionState next = importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(next, instanceOf(ImportPathPrompt.class));
    }

    @Test
    public void returnToMenuOnEmptyInput() {
        Connection connection = Nimue.connection();
        connection.getInputQueue().push("");

        ConnectionState next = importPathPromptHandler.execute(connection, Nimue.configuration());

        assertThat(next, instanceOf(AdministrationMenu.class));
    }

    private class MockDatabaseImporter extends DatabaseImporter {
        public ImportConfiguration importConfiguration;

        @Override
        public void startWith(ImportConfiguration importConfiguration) {
            this.importConfiguration = importConfiguration;
        }
    }
}