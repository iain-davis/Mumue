package org.mumue.mumue.importer.stages;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mumue.mumue.importer.GlobalConstants;
import org.mumue.mumue.importer.ImportBucket;
import org.mumue.mumue.importer.components.Component;

public class ContentsChainStageTest {
    ContentsChainStage stage = new ContentsChainStage();

    @Test
    public void emptyContents() {
        ImportBucket bucket = new ImportBucket();
        Long id = RandomUtils.insecure().randomLong(1, 100);
        bucket.getComponents().put(id, new Component().withId(id));
        stage.run(bucket);
        Assert.assertEquals(0, bucket.getComponents().get(id).getContents().size());
    }

    @Test
    public void onlyOneInContents() {
        ImportBucket bucket = new ImportBucket();
        Long id = RandomUtils.insecure().randomLong(1, 100);
        Component component = new Component().withId(id);
        bucket.getComponents().put(id, component);

        Long contentId = RandomUtils.insecure().randomLong(100, 200);
        Component content = new Component().withId(contentId);
        bucket.getComponents().put(contentId, content);
        component.getContents().add(content);
        buildNext(bucket, contentId, GlobalConstants.REFERENCE_UNKNOWN);
        stage.run(bucket);
        Assert.assertEquals(1, bucket.getComponents().get(id).getContents().size());
        Assert.assertEquals(contentId, bucket.getComponents().get(id).getContents().get(0).getId());
    }

    @Test
    public void multipleInContents() {
        ImportBucket bucket = new ImportBucket();
        Long id = RandomUtils.insecure().randomLong(1, 100);
        Component component = new Component().withId(id);
        bucket.getComponents().put(id, component);
        int count = RandomUtils.insecure().randomInt(3, 4);
        List<Long> contentIds = new ArrayList<>();

        long contentId = RandomUtils.insecure().randomLong(300, 400);
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
        Assert.assertEquals(count, bucket.getComponents().get(id).getContents().size());
        for (int i = 0; i < count; i++) {
            Assert.assertEquals(contentIds.get(i), bucket.getComponents().get(id).getContents().get(i).getId());
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
