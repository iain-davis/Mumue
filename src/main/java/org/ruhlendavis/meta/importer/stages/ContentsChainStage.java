package org.ruhlendavis.meta.importer.stages;

import org.ruhlendavis.meta.GlobalConstants;
import org.ruhlendavis.meta.components.Component;
import org.ruhlendavis.meta.importer.ImportBucket;
import org.ruhlendavis.meta.importer.ImporterStage;

import java.util.Map.Entry;

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
        while (current != null && !current.getId().equals(GlobalConstants.REFERENCE_UNKNOWN)) {
            Long id = Long.parseLong(bucket.getComponentLines().get(current.getId()).get(4));
            if (id.equals(GlobalConstants.REFERENCE_UNKNOWN)) {
                return;
            }
            current = getComponent(bucket, id);
            component.getContents().add(current);
        }
    }
}
