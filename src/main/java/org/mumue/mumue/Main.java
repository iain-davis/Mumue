package org.mumue.mumue;

public class Main {
    private static Launcher launcher = new Launcher();

    public static void main(String... arguments) {
        launcher.launch(arguments);
    }

    public static void setLauncher(Launcher launcher) {
        Main.launcher = launcher;
    }
}
