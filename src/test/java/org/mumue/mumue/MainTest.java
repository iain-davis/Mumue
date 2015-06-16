package org.mumue.mumue;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MainTest {
    @Test
    public void mainLaunchesApplication() {
        Launcher launcher = mock(Launcher.class);
        Main.setLauncher(launcher);

        Main.main();

        verify(launcher).launch();
    }

    @Test
    public void mainPassesArgumentsAlong() {
        String[] arguments = {RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(8), "--test"};
        Launcher launcher = mock(Launcher.class);
        Main.setLauncher(launcher);

        Main.main(arguments);

        verify(launcher).launch(arguments);
    }
}
