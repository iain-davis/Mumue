package org.ruhlendavis.meta;

import org.apache.commons.lang3.StringUtils;

public class ThreadFactory {
    public Thread create(Runnable runnable, String name) {
        if (StringUtils.isBlank(name)) {
            return new Thread(runnable);
        }
        return new Thread(runnable, name);
    }

    public Thread create(Runnable runnable) {
        return new Thread(runnable);
    }
}
