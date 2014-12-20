package org.ruhlendavis.meta.importer.stages;

import java.util.Map.Entry;

import org.ruhlendavis.meta.importer.GlobalConstants;
import org.ruhlendavis.meta.components.Component;
import org.ruhlendavis.meta.importer.ImportBucket;
import org.ruhlendavis.meta.importer.ImporterStage;

public class ContentsChainStage extends ImporterStage {
    @Override
    public void run(ImportBucket bucket) {
        for (Entry<Long, Component> entry : bucket.getComponents().entrySet()) {
            processComponent(bucket, entry.getValue());
        }

    }

    private void processComponent(ImportBucket bucket, Component component) {
        if (component.getContents().size() == 0) {
            return;
        }

        Component current = component.getContents().get(0);
        while (current != null && !current.getReference().equals(GlobalConstants.REFERENCE_UNKNOWN)) {
            Long id = Long.parseLong(bucket.getComponentLines().get(current.getReference()).get(4));
            if (id.equals(GlobalConstants.REFERENCE_UNKNOWN)) {
                return;
            }
            current = getComponent(bucket, id);
            component.getContents().add(current);
        }
    }
}
