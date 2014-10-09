package org.ruhlendavis.meta.importer;

import org.junit.Assert;
import org.junit.Test;
import org.ruhlendavis.meta.components.*;
import org.ruhlendavis.meta.components.Character;

import java.io.IOException;
import java.util.Map;

public class GenerateRawComponentsStageTest {
    private GenerateRawComponentsStage stage = new GenerateRawComponentsStage();
    private ImportBucket bucket = new ImportBucket();
    @Test
    public void generateRawComponentsGeneratesSpace() throws IOException {
        String file = "C:\\Users\\Feaelin\\Documents\\Actual Data\\Programming\\Meta\\src\\test\\resources\\space.db";
        bucket.setFile(file);
        stage.run(bucket);
        Assert.assertEquals(1, bucket.getComponents().size());
        Assert.assertTrue(bucket.getComponents().get(0L) instanceof Space);
    }

    @Test
    public void generateRawComponentsGeneratesCharacter() throws IOException {
        String file = "C:\\Users\\Feaelin\\Documents\\Actual Data\\Programming\\Meta\\src\\test\\resources\\character.db";
        bucket.setFile(file);
        stage.run(bucket);
        Assert.assertEquals(1, bucket.getComponents().size());
        Assert.assertTrue(bucket.getComponents().get(1L) instanceof org.ruhlendavis.meta.components.Character);
    }

    @Test
    public void generateRawComponents() throws IOException {
        String file = "C:\\Users\\Feaelin\\Documents\\Actual Data\\Programming\\Meta\\src\\test\\resources\\glow.db";
        bucket.setFile(file);
        stage.run(bucket);
        Assert.assertEquals(2, bucket.getComponents().size());
        Assert.assertTrue(bucket.getComponents().get(0L) instanceof Space);
        Assert.assertTrue(bucket.getComponents().get(1L) instanceof Character);
    }
}
