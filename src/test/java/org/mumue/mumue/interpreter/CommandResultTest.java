package org.mumue.mumue.interpreter;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CommandResultTest {
    private final CommandResult result = new CommandResult();

    @Test
    public void statusDefaultsToUnknown() {
        assertThat(result.getStatus(), equalTo(CommandStatus.UNKNOWN_COMMAND));
    }
}
