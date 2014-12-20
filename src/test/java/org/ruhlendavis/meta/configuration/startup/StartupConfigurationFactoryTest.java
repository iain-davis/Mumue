package org.ruhlendavis.meta.configuration.startup;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StartupConfigurationFactoryTest {
    @Mock File file;
    @Mock FileFactory fileFactory;
    @InjectMocks StartupConfigurationFactory startupConfigurationFactory;
    @Rule public ExpectedException thrown = ExpectedException.none();

    @Test
    public void neverReturnNull() {
        when(fileFactory.create("")).thenReturn(file);
        when(file.exists()).thenReturn(true);
        assertNotNull(startupConfigurationFactory.create(""));
    }

    @Test
    public void unknownPathThrowsException() throws IOException {
        String path = RandomStringUtils.randomAlphabetic(15);
        when(fileFactory.create(path)).thenReturn(file);
        when(file.exists()).thenReturn(false);

        thrown.expect(StartupConfigurationNotFound.class);
        thrown.expectMessage("Startup configuration file '" + path + "' not found.");

        startupConfigurationFactory.create(path);
    }

    @Test
    public void pathThatIsDirectoryThrowsException() throws IOException {
        String path = RandomStringUtils.randomAlphabetic(15);
        when(fileFactory.create(path)).thenReturn(file);
        when(file.exists()).thenReturn(true);
        when(file.isDirectory()).thenReturn(true);

        thrown.expect(StartupConfigurationNotFound.class);
        thrown.expectMessage("Startup configuration file '" + path + "' not found.");

        startupConfigurationFactory.create(path);
    }
}
