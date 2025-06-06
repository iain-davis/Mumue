package org.mumue.mumue.importer;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mumue.mumue.importer.stages.ContentsChainStage;
import org.mumue.mumue.importer.stages.GenerateRawComponentsStage;
import org.mumue.mumue.importer.stages.LinkSourceChainStage;
import org.mumue.mumue.importer.stages.LoadLinesStage;
import org.mumue.mumue.importer.stages.PlayerGenerationStage;
import org.mumue.mumue.importer.stages.ProcessComponentLinesStage;
import org.mumue.mumue.importer.stages.SeparateSectionsStage;

@RunWith(MockitoJUnitRunner.class)
public class ImporterTest {
    @Mock
    LoadLinesStage loadLinesStage;
    @Mock
    ImportBucket bucket;

    @InjectMocks
    private Importer importer = new Importer();

    @Test
    public void importerAddsAllStages() {
        int index = 0;
        assertTrue(importer.getStages().get(index++).getClass().equals(LoadLinesStage.class));
        assertTrue(importer.getStages().get(index++).getClass().equals(SeparateSectionsStage.class));
        assertTrue(importer.getStages().get(index++).getClass().equals(GenerateRawComponentsStage.class));
        assertTrue(importer.getStages().get(index++).getClass().equals(ProcessComponentLinesStage.class));
        assertTrue(importer.getStages().get(index++).getClass().equals(ContentsChainStage.class));
        assertTrue(importer.getStages().get(index++).getClass().equals(LinkSourceChainStage.class));
        assertTrue(importer.getStages().get(index).getClass().equals(PlayerGenerationStage.class));
    }

    @Test
    public void runExecutesOneStage() {
        List<ImporterStage> stages = new ArrayList<>();
        stages.add(loadLinesStage);
        importer.setStages(stages);
        importer.run("file");
        verify(loadLinesStage).run(any(ImportBucket.class));
    }

    @Test
    public void runDoesNotExecuteFailedBucket() {
        List<ImporterStage> stages = new ArrayList<>();
        stages.add(loadLinesStage);
        importer.setStages(stages);
        when(bucket.isFailed()).thenReturn(true);
        importer.run("file");
        verify(loadLinesStage, never()).run(any(ImportBucket.class));
    }
}
