package org.ruhlendavis.meta.importer.stages;

import org.apache.commons.io.FileUtils;
import org.ruhlendavis.meta.importer.ImportBucket;
import org.ruhlendavis.meta.importer.ImporterStage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoadLinesStage extends ImporterStage {
    @Override
    public void run(ImportBucket bucket) {
        Scanner scanner= null;
        try {
            scanner = new Scanner(FileUtils.getFile(bucket.getFile()), "US-ASCII");
            scanner.useDelimiter("\\x0A");
            while(scanner.hasNext())
            {
                String line = scanner.next();
                bucket.getSourceLines().add(line);
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
