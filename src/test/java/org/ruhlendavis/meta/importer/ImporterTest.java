package org.ruhlendavis.meta.importer;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ImporterTest {
    @Mock
    LoadLinesStage loadLinesStage;

    @InjectMocks
    private Importer importer = new Importer();

    @Test
    public void importerAddsAllStages() {
        assertTrue(importer.getStages().get(0).getClass().equals(LoadLinesStage.class));
        assertTrue(importer.getStages().get(1).getClass().equals(SeparateSectionsStage.class));
    }

    @Test
    public void runExecutesOneStage() {
        List<ImporterStage> stages = new ArrayList<>();
        stages.add(loadLinesStage);
        importer.setStages(stages);
        importer.run("file");
        verify(loadLinesStage).run(any(ImportBucket.class));
    }

//    @Test
//    public void f() {
//        importer.run("C:\\Users\\Feaelin\\Documents\\Actual Data\\Programming\\Meta\\src\\test\\resources\\narnia.db");
//    }
}
