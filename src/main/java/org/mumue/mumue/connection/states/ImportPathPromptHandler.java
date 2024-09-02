package org.mumue.mumue.connection.states;

import java.io.File;

import jakarta.inject.Inject;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Singleton;
import org.apache.commons.io.FileUtils;
import org.mumue.mumue.configuration.ApplicationConfiguration;
import org.mumue.mumue.connection.Connection;
import org.mumue.mumue.databaseimporter.DatabaseImportLauncher;
import org.mumue.mumue.databaseimporter.ImportConfiguration;
import org.mumue.mumue.text.TextMaker;
import org.mumue.mumue.text.TextName;

@Singleton
class ImportPathPromptHandler implements ConnectionState {
    private final ConnectionStateProvider connectionStateProvider;
    private final DatabaseImportLauncher databaseImportLauncher;
    private final TextMaker textMaker;

    @Inject
    ImportPathPromptHandler(ConnectionStateProvider connectionStateProvider, DatabaseImportLauncher databaseImportLauncher, TextMaker textMaker) {
        this.databaseImportLauncher = databaseImportLauncher;
        this.connectionStateProvider = connectionStateProvider;
        this.textMaker = textMaker;
    }

    @Override
    public ConnectionState execute(Connection connection, ApplicationConfiguration configuration) {
        if (connection.getInputQueue().hasAny()) {
            String path = connection.getInputQueue().pop();
            if (path.isEmpty()) {
                return connectionStateProvider.get(AdministrationMenu.class);
            }
            File file = FileUtils.getFile(path);
            if (file.exists()) {
                ImportConfiguration importConfiguration = new ImportConfiguration();
                importConfiguration.setFile(file);
                databaseImportLauncher.launchWith(importConfiguration);
                return connectionStateProvider.get(AdministrationMenu.class);
            } else {
                String text = textMaker.getText(TextName.FileNotFound, connection.getLocale(), ImmutableMap.of("file path", path));
                connection.getOutputQueue().push(text);
                return connectionStateProvider.get(ImportPathPrompt.class);
            }
        }
        return this;
    }
}
