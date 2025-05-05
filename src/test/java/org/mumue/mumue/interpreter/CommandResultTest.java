package org.mumue.mumue.interpreter;

import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


class CommandResultTest {
    private final CommandResult result = new CommandResult();

    @Test
    void statusDefaultsToUnknown() {
        assertThat(result.getStatus(), equalTo(CommandStatus.UNKNOWN_COMMAND));
    }
}
