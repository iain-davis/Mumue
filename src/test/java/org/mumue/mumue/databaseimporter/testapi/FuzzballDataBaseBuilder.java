package org.mumue.mumue.databaseimporter.testapi;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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
        try (PrintWriter writer = new PrintWriter(file) {
            @Override
            public void println(String line) {
                print(line + "\n");
            }
        }) {
            lineBuilder.getLines().forEach(writer::println);
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
}
