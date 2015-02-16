package org.ruhlendavis.meta.connection.stages;

import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NoOperationStageTest {
    @Test
    public void returnSame() {
        NoOperationStage stage = new NoOperationStage();
        assertThat(stage.execute(null, null, null), sameInstance(stage));
    }
}
