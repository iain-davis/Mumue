package org.ruhlendavis.meta.listener;

import org.ruhlendavis.meta.connection.ConnectionInputReceiver;

public class ThreadFactory {
    public Thread createThread(ConnectionInputReceiver connectionInputReceiver, String name) {
        return new Thread(connectionInputReceiver, name);
    }
}
