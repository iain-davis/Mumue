package org.ruhlendavis.meta.acceptance;

import org.junit.Test;

import org.ruhlendavis.meta.Main;

public class CommandLineAcceptanceTest {
    Main main = new Main();

    @Test
    public void doNotRunForeverInTestMode() {
        main.run("--test");
    }
}
