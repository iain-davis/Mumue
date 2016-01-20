package org.mumue.mumue.configuration;

public class PortConfiguration {
    private int port;
    private PortType type;
    private boolean supportsMenus;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public PortType getType() {
        return type;
    }

    public void setType(PortType type) {
        this.type = type;
    }

    public boolean isSupportsMenus() {
        return supportsMenus;
    }

    public void setSupportsMenus(boolean supportsMenus) {
        this.supportsMenus = supportsMenus;
    }
}
