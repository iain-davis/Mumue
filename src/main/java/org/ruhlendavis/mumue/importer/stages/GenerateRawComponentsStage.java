package org.ruhlendavis.mumue.importer.stages;

import java.util.List;
import java.util.Map.Entry;

import org.ruhlendavis.mumue.componentsold.Artifact;
import org.ruhlendavis.mumue.componentsold.GameCharacter;
import org.ruhlendavis.mumue.componentsold.Link;
import org.ruhlendavis.mumue.componentsold.Program;
import org.ruhlendavis.mumue.componentsold.Space;
import org.ruhlendavis.mumue.importer.ImportBucket;
import org.ruhlendavis.mumue.importer.ImporterStage;

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
