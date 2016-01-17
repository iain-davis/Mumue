package org.mumue.mumue.text.transformer;

public enum Color {
    Reset                ("0",    "^NORMAL^"),
    ResetShortHand       ("0",    "^ ^"),
    BlackForeground      ("30",   "^BLACK^"),
    DarkRedForeground    ("31",   "^CRIMSON^"),
    DarkGreenForeground  ("32",   "^FOREST^"),
    DarkYellowForeground ("33",   "^BROWN^"),
    DarkBlueForeground   ("34",   "^NAVY^"),
    DarkPurpleForeground ("35",   "^VIOLET^"),
    DarkCyanForeground   ("36",   "^AQUA^"),
    GrayForeground       ("37",   "^GRAY^"),
    DarkGrayForeground   ("30;1", "^GLOOM^"),
    RedForeground        ("31;1", "^RED^"),
    GreenForeground      ("32;1", "^GREEN^"),
    YellowForeground     ("33;1", "^YELLOW^"),
    BlueForeground       ("34;1", "^BLUE^"),
    MagentaForeground    ("35;1", "^PURPLE^"),
    CyanForeground       ("36;1", "^CYAN^"),
    WhiteForeground      ("37;1", "^WHITE^"),
    BlackBackground      ("40",   "^BBLACK^"),
    RedBackground        ("41",   "^BRED^"),
    GreenBackground      ("42",   "^BGREEN^"),
    YellowBackground     ("43",   "^BBROWN^"),
    BlueBackground       ("44",   "^BBLUE^"),
    MagentaBackground    ("45",   "^BPURPLE^"),
    CyanBackground       ("46",   "^BCYAN^"),
    WhiteBackground      ("47",   "^BGRAY^"),
    ;

    private final String ansiCode;
    private final String glowName;

    Color(String ansiCode, String glowName) {
        this.ansiCode = ansiCode;
        this.glowName = glowName;
    }

    public String getGlowCode() {
        return glowName;
    }

    public String getAnsiCode() {
        return ansiCode;
    }
}
