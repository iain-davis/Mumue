package org.ruhlendavis.meta.listener;

public class ThreadFactory {
    public Thread createThread(Connection connection, String name) {
        return new Thread(connection, name);
    }
}
