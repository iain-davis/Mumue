package org.ruhlendavis.meta.importer;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.ruhlendavis.meta.GlobalConstants;
import org.ruhlendavis.meta.importer.ImportBucket;
import org.ruhlendavis.meta.importer.ImporterStage;

import static org.junit.Assert.assertEquals;

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
        String line = ((Integer)(type + 24)).toString();
        assertEquals(3, stage.determineType(line));
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
