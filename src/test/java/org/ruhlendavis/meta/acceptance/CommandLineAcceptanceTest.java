package org.ruhlendavis.meta.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.Main;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineAcceptanceTest {
    private Main main = new Main();

    @Test
    public void doNotRunForeverInTestMode() {
        main.run("--test");
    }

//    @Test
//    public void useSpecifiedStartupConfigurationPath() throws URISyntaxException {
//        String path = Resources.getResource(TestConstants.ACCEPTANCE_STARTUP_CONFIGURATION_PATH).toURI().getPath();
//        main.run("-s", path, "--test");
//
//    }
}
