package org.ruhlendavis.meta.importer;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ImporterTest {
    @Mock
    LoadLinesStage loadLinesStage;

    @InjectMocks
    private Importer importer = new Importer();

    @Test
    public void runExecutesLoadLinesStage() {
        List<ImporterStage> stages = new ArrayList<>();
        stages.add(loadLinesStage);
        importer.setStages(stages);
        importer.run("file");
        verify(loadLinesStage).run(any(ImportBucket.class));
    }

    @Ignore
    @Test
    public void f() {
        importer.run("C:\\Users\\Feaelin\\Documents\\Actual Data\\Programming\\Meta\\src\\test\\resources\\university.db");
    }
}
