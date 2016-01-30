package org.mumue.mumue.databaseimporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class LineLoader {
    public List<String> loadFrom(File file) {
        List<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file, "US-ASCII");
            scanner.useDelimiter("\\x0A");
            while (scanner.hasNext()) {
                lines.add(scanner.next());
            }
        } catch (FileNotFoundException exception) {
            throw new RuntimeException(exception);
        }
        return lines;
    }
}
