package org.ruhlendavis.meta.importer.stages;

import java.util.List;
import java.util.Map.Entry;

import org.ruhlendavis.meta.componentsold.Artifact;
import org.ruhlendavis.meta.componentsold.GameCharacter;
import org.ruhlendavis.meta.componentsold.Link;
import org.ruhlendavis.meta.componentsold.Program;
import org.ruhlendavis.meta.componentsold.Space;
import org.ruhlendavis.meta.importer.ImportBucket;
import org.ruhlendavis.meta.importer.ImporterStage;

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
