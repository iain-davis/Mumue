package org.mumue.mumue.databaseimporter;

enum FuzzballDatabaseItemType {
    CHARACTER (4),
    EXIT      (2),
    GARBAGE   (0),
    PROGRAM   (1),
    ROOM      (3),
    THING     (4)
    ;

    private final int codaSize;
    FuzzballDatabaseItemType(int codaSize) {
        this.codaSize = codaSize;
    }

    public int getCodaSize() {
        return codaSize;
    }

    public static FuzzballDatabaseItemType fromLine(String line) {
        int type = Integer.parseInt(line.split(" ")[0]) & 0x7;
        switch (type) {
            case 0:
                return ROOM;
            case 1:
                return THING;
            case 2:
                return EXIT;
            case 3:
                return CHARACTER;
            case 4:
                return PROGRAM;
            case 5:
                return GARBAGE;
            default:
                throw new RuntimeException("Unexpected Fuzzball Database Item Type in line '" + line + "'");
        }
    }
}
