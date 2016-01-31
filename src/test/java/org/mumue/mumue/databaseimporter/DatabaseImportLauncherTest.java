package org.mumue.mumue.databaseimporter;

import static org.mockito.Mockito.mock;

import java.util.concurrent.ExecutorService;

public class DatabaseImportLauncherTest {
    private final DatabaseImporter importer = mock(DatabaseImporter.class);
    private final ExecutorService executorService = mock(ExecutorService.class);
    private final DatabaseImportLauncher launcher = new DatabaseImportLauncher(importer, executorService);
}