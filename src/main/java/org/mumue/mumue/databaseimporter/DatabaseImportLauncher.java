package org.mumue.mumue.databaseimporter;

import java.util.concurrent.ExecutorService;
import jakarta.inject.Inject;

public class DatabaseImportLauncher {
    private final DatabaseImporter databaseImporter;
    private final ExecutorService executorService;

    @Inject
    public DatabaseImportLauncher(DatabaseImporter databaseImporter, ExecutorService executorService) {
        this.databaseImporter = databaseImporter;
        this.executorService = executorService;
    }

    public void launchWith(ImportConfiguration importConfiguration) {
        executorService.submit(() -> databaseImporter.importUsing(importConfiguration));
    }
}
