package org.ruhlendavis.meta.importer.stages;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import org.ruhlendavis.meta.components.Component;
import org.ruhlendavis.meta.importer.GlobalConstants;
import org.ruhlendavis.meta.importer.ImportBucket;

public class ContentsChainStageTest {
    ContentsChainStage stage = new ContentsChainStage();

    @Test
    public void emptyContents() {
        ImportBucket bucket = new ImportBucket();
        Long id = RandomUtils.nextLong(1, 100);
        bucket.getComponents().put(id, new Component().withId(id));
        stage.run(bucket);
        assertEquals(0, bucket.getComponents().get(id).getContents().size());
    }

    @Test
    public void onlyOneInContents() {
        ImportBucket bucket = new ImportBucket();
        Long id = RandomUtils.nextLong(1, 100);
        Component component = new Component().withId(id);
        bucket.getComponents().put(id, component);

        Long contentId = RandomUtils.nextLong(100, 200);
        Component content = new Component().withId(contentId);
        bucket.getComponents().put(contentId, content);
        component.getContents().add(content);
        buildNext(bucket, contentId, GlobalConstants.REFERENCE_UNKNOWN);
        stage.run(bucket);
        assertEquals(1, bucket.getComponents().get(id).getContents().size());
        assertEquals(contentId, bucket.getComponents().get(id).getContents().get(0).getId());
    }

    @Test
    public void multipleInContents() {
        ImportBucket bucket = new ImportBucket();
        Long id = RandomUtils.nextLong(1, 100);
        Component component = new Component().withId(id);
        bucket.getComponents().put(id, component);
        int count = RandomUtils.nextInt(3, 4);
        List<Long> contentIds = new ArrayList<>();

        long contentId = RandomUtils.nextLong(300, 400);
        Component content = new Component().withId(contentId);
        component.getContents().add(content);
        contentIds.add(contentId);
        for (int i = 0; i < count; i++) {
            Long nextContentId = 100L + i;
            buildNext(bucket, contentId, nextContentId);
            contentId = nextContentId;
            contentIds.add(nextContentId);
        }
        buildNext(bucket, contentId, GlobalConstants.REFERENCE_UNKNOWN);
        count++;

        stage.run(bucket);
        assertEquals(count, bucket.getComponents().get(id).getContents().size());
        for (int i = 0; i < count; i++) {
            assertEquals(contentIds.get(i), bucket.getComponents().get(id).getContents().get(i).getId());
        }
    }

    private void buildNext(ImportBucket bucket, long contentId, Long nextContentId) {
        Component nextContent = new Component().withId(nextContentId);
        bucket.getComponents().put(nextContentId, nextContent);
        List<String> lines = new ArrayList<>();
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add(nextContentId.toString());
        bucket.getComponentLines().put(contentId, lines);
    }
}
