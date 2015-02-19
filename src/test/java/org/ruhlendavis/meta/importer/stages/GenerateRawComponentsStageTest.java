package org.ruhlendavis.meta.importer.stages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import org.ruhlendavis.meta.componentsold.Component;
import org.ruhlendavis.meta.componentsold.Artifact;
import org.ruhlendavis.meta.componentsold.GameCharacter;
import org.ruhlendavis.meta.componentsold.Link;
import org.ruhlendavis.meta.componentsold.Program;
import org.ruhlendavis.meta.componentsold.Space;
import org.ruhlendavis.meta.importer.ImportBucket;
import org.ruhlendavis.meta.importer.ImporterStageTestHelper;

public class GenerateRawComponentsStageTest extends ImporterStageTestHelper {
    GenerateRawComponentsStage stage = new GenerateRawComponentsStage();

    @Test
    public void runHandlesNoComponents() {
        ImportBucket bucket = new ImportBucket();
        stage.run(bucket);
        assertEquals(0, bucket.getComponents().size());
    }

    @Test
    public void runHandlesOneSpaceComponent() {
        String id = RandomStringUtils.randomNumeric(3);
        ImportBucket bucket = setupImportBucket("0", id);
        stage.run(bucket);
        assertEquals(1, bucket.getComponents().size());
        Component component = bucket.getComponents().get(Long.parseLong(id));
        assertNotNull(component);
        assertTrue(component instanceof Space);
        assertEquals(Long.parseLong(id), bucket.getComponents().get(Long.parseLong(id)).getId(), 0);
    }

    @Test
    public void runHandlesOneMultipleComponents() {
        String id1 = RandomStringUtils.randomNumeric(3);
        String id2 = RandomStringUtils.randomNumeric(3);
        ImportBucket bucket = setupImportBucket("0", id1);
        List<String> lines = new ArrayList<>();
        addOneDatabaseItemToList(lines, id2, "1", 0, 0);
        bucket.getComponentLines().put(Long.parseLong(id2), lines);
        stage.run(bucket);
        assertEquals(2, bucket.getComponents().size());
        Component component = bucket.getComponents().get(Long.parseLong(id1));
        assertNotNull(component);
        assertTrue(component instanceof Space);
        assertEquals(Long.parseLong(id1), component.getId(), 0);
        component = bucket.getComponents().get(Long.parseLong(id2));
        assertNotNull(component);
        assertTrue(component instanceof Artifact);
        assertEquals(Long.parseLong(id2), component.getId(), 0);
    }

    @Test
    public void runHandlesOneArtifactComponent() {
        String id = RandomStringUtils.randomNumeric(3);
        ImportBucket bucket = setupImportBucket("1", id);
        stage.run(bucket);
        assertEquals(1, bucket.getComponents().size());
        Component component = bucket.getComponents().get(Long.parseLong(id));
        assertNotNull(component);
        assertTrue(component instanceof Artifact);
        assertEquals(Long.parseLong(id), component.getId(), 0);
    }

    @Test
    public void runHandlesOneLinkComponent() {
        String id = RandomStringUtils.randomNumeric(3);
        ImportBucket bucket = setupImportBucket("2", id);
        stage.run(bucket);
        assertEquals(1, bucket.getComponents().size());
        Component component = bucket.getComponents().get(Long.parseLong(id));
        assertNotNull(component);
        assertTrue(component instanceof Link);
        assertEquals(Long.parseLong(id), component.getId(), 0);
    }

    @Test
    public void runHandlesOneCharacterComponent() {
        String id = RandomStringUtils.randomNumeric(3);
        ImportBucket bucket = setupImportBucket("3", id);
        stage.run(bucket);
        assertEquals(1, bucket.getComponents().size());
        Component component = bucket.getComponents().get(Long.parseLong(id));
        assertNotNull(component);
        assertTrue(component instanceof GameCharacter);
        assertEquals(Long.parseLong(id), component.getId(), 0);
    }

    @Test
    public void runHandlesOneProgramComponent() {
        String id = RandomStringUtils.randomNumeric(3);
        ImportBucket bucket = setupImportBucket("4", id);
        stage.run(bucket);
        assertEquals(1, bucket.getComponents().size());
        Component component = bucket.getComponents().get(Long.parseLong(id));
        assertNotNull(component);
        assertTrue(component instanceof Program);
        assertEquals(Long.parseLong(id), component.getId(), 0);
    }

    private ImportBucket setupImportBucket(String flags, String id) {
        ImportBucket bucket = new ImportBucket();
        List<String> lines = new ArrayList<>();
        addOneDatabaseItemToList(lines, id, flags, 0, 0);
        bucket.getComponentLines().put(Long.parseLong(id), lines);
        return bucket;
    }
}
