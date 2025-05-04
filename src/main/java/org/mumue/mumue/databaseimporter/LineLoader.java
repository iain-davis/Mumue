package org.mumue.mumue.databaseimporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class LineLoader {
    public List<String> loadFrom(File file) {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(file, StandardCharsets.US_ASCII)) {
            scanner.useDelimiter("\\x0A");
            while (scanner.hasNext()) {
                lines.add(scanner.next());
            }
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
        return lines;
    }
}
