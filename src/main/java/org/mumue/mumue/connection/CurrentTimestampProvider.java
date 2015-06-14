package org.mumue.mumue.connection;

import java.time.Instant;

public class CurrentTimestampProvider {
    public Instant get() {
        return Instant.now();
    }
}
