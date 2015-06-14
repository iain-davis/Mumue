package org.mumue.mumue.importer.stages;

import java.util.Map.Entry;

import org.mumue.mumue.importer.ImportBucket;
import org.mumue.mumue.importer.ImporterStage;
import org.mumue.mumue.importer.components.Component;
import org.mumue.mumue.importer.components.Link;
import org.mumue.mumue.importer.components.LinkSource;

public class LinkSourceChainStage extends ImporterStage {
    @Override
    public void run(ImportBucket bucket) {
        for (Entry<Long, Component> entry : bucket.getComponents().entrySet()) {
            if (entry.getValue() instanceof LinkSource) {
                processComponent(bucket, (LinkSource)entry.getValue());
            }
        }
    }

    private void processComponent(ImportBucket bucket, LinkSource component) {
        if (component.getLinks().size() == 0) {
            return;
        }
        Link current = component.getLinks().get(0);
        while (hasNext(bucket, current)) {
            String id = bucket.getComponentLines().get(current.getId()).get(4);
            current = (Link)getComponent(bucket, id);
            component.getLinks().add(current);
        }
    }

    private boolean hasNext(ImportBucket bucket, Link current) {
        return !bucket.getComponentLines().get(current.getId()).get(4).equals("-1");
    }
}
