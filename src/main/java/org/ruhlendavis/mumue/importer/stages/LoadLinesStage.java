package org.ruhlendavis.mumue.importer.stages;

import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import org.ruhlendavis.mumue.importer.ImportBucket;
import org.ruhlendavis.mumue.importer.ImporterStage;

public class LoadLinesStage extends ImporterStage {
    @Override
    public void run(ImportBucket bucket) {
        Scanner scanner;
        try {
            scanner = new Scanner(FileUtils.getFile(bucket.getFile()), "US-ASCII");
            scanner.useDelimiter("\\x0A");
            while(scanner.hasNext())
            {
                String line = scanner.next();
                bucket.getSourceLines().add(line);
            }
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }
}
