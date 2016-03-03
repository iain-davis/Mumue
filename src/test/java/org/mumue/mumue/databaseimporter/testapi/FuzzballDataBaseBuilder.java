package org.mumue.mumue.databaseimporter.testapi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.junit.rules.TemporaryFolder;

public class FuzzballDataBaseBuilder {
    private final TemporaryFolder temporaryFolder;
    private final LineBuilder lineBuilder;

    public FuzzballDataBaseBuilder(TemporaryFolder temporaryFolder, LineBuilder lineBuilder) {
        this.temporaryFolder = temporaryFolder;
        this.lineBuilder = lineBuilder;
    }

    public File build() {
        File file = createFile();
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file))) {
            for (String line : lineBuilder.getLines()) {
                writeLine(writer, line);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return file;
    }

    private File createFile() {
        try {
            return temporaryFolder.newFile();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void writeLine(OutputStreamWriter writer, String line) {
        try {
            writer.write(line + "\n");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
