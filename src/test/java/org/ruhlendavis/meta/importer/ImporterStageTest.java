package org.ruhlendavis.meta.importer;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.ruhlendavis.meta.GlobalConstants;

public class ImporterStageTest {
    ImporterStage stage = new ImporterStage() {
        @Override
        public void run(ImportBucket bucket) {}
    };

    @Test
    public void determineTypeReturnsType() {
        Integer type = RandomUtils.nextInt(0, 5);
        String line = type.toString();
        assertEquals(type, stage.determineType(line), 0);
    }

    @Test
    public void determineTypeWithFlagsReturnsType() {
        Integer type = RandomUtils.nextInt(0, 5);
        String line = Integer.toString(type + 24);
        assertEquals(type, stage.determineType(line), 0);
    }

    @Test
    public void determineTypeWithSecondFlagSetReturnsType() {
        Integer type = RandomUtils.nextInt(0, 5);
        String line = type.toString() + " 3";
        assertEquals(type, stage.determineType(line), 0);
    }

    @Test
    public void parseReferenceConvertsStringToLong() {
        String string = RandomStringUtils.randomNumeric(5);
        Long l = Long.parseLong(string);
        assertEquals(l, stage.parseReference(string));
    }

    @Test
    public void parseReferenceHandlesHashMark() {
        String string = RandomStringUtils.randomNumeric(5);
        Long l = Long.parseLong(string);
        assertEquals(l, stage.parseReference("#" + string));
    }

    @Test
    public void parseReferenceWithNullReturnsUnknown() {
        assertEquals(GlobalConstants.REFERENCE_UNKNOWN, stage.parseReference(null), 0);
    }

    @Test
    public void parseReferenceWithBlankReturnsUnknown() {
        assertEquals(GlobalConstants.REFERENCE_UNKNOWN, stage.parseReference(""), 0);
    }
}
