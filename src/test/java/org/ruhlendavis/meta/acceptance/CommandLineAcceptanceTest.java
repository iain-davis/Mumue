package org.ruhlendavis.meta.acceptance;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

import com.google.common.io.Resources;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.meta.Main;
import org.ruhlendavis.meta.configuration.commandline.CommandLineOptionName;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineAcceptanceTest {
    @Test
    public void doNotRunForeverInTestMode() {
        Main.main("--test");
    }
//
//    @Test
//    public void useSpecifiedStartupConfigurationPath() throws URISyntaxException, InterruptedException {
//        String propertiesPath = Resources.getResource(TestConstants.ACCEPTANCE_STARTUP_CONFIGURATION_PATH).toURI().getPath();
//        FileUtils.deleteQuietly(new File(TestConstants.ACCEPTANCE_DATABASE_PATH));
//        Main.main("--test", "--" + CommandLineOptionName.STARTUP_CONFIGURATION_PATH, propertiesPath);
//        File file = new File(TestConstants.ACCEPTANCE_DATABASE_PATH);
//        assertTrue(file.exists());
//        FileUtils.deleteQuietly(file);
//    }
}
