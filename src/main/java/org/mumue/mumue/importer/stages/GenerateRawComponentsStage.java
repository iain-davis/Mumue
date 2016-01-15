package org.mumue.mumue.importer.stages;

import java.util.List;
import java.util.Map.Entry;

import org.mumue.mumue.importer.ImportBucket;
import org.mumue.mumue.importer.components.GameCharacter;
import org.mumue.mumue.importer.components.Space;
import org.mumue.mumue.importer.ImporterStage;
import org.mumue.mumue.importer.components.Artifact;
import org.mumue.mumue.importer.components.Link;
import org.mumue.mumue.importer.components.Program;

public class GenerateRawComponentsStage extends ImporterStage {
    @Override
    public void run(ImportBucket bucket) {
        for (Entry<Long, List<String>> entry : bucket.getComponentLines().entrySet()) {
            long type = determineType(entry.getValue().get(ITEM_FLAGS_INDEX));
            if (type == 0) {
                bucket.getComponents().put(entry.getKey(), new Space().withId(entry.getKey()));
            } else if (type == 1) {
                bucket.getComponents().put(entry.getKey(), new Artifact().withId(entry.getKey()));
            } else if (type == 2) {
                bucket.getComponents().put(entry.getKey(), new Link().withId(entry.getKey()));
            } else if (type == 3) {
                bucket.getComponents().put(entry.getKey(), new GameCharacter().withId(entry.getKey()));
            } else if (type == 4) {
                bucket.getComponents().put(entry.getKey(), new Program().withId(entry.getKey()));
            }
        }
    }
}
