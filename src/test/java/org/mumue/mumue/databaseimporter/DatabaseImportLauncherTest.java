package org.mumue.mumue.databaseimporter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ExecutorService;

import org.junit.Test;

public class DatabaseImportLauncherTest {
    private final DatabaseImporter importer = mock(DatabaseImporter.class);
    private final ExecutorService executorService = mock(ExecutorService.class);
    private final DatabaseImportLauncher launcher = new DatabaseImportLauncher(importer, executorService);

    @Test
    public void launchesDatabaseImporter() {
      launcher.launchWith(new ImportConfiguration());

        verify(executorService).submit(any(Runnable.class));
    }
}