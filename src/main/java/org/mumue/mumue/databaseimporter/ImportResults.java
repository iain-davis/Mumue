package org.mumue.mumue.databaseimporter;

import org.mumue.mumue.components.universe.Universe;

class ImportResults {
    private long componentCount;
    private long parameterCount;
    private Universe universe = new Universe();

    public long getComponentCount() {
        return componentCount;
    }

    public void setComponentCount(long componentCount) {
        this.componentCount = componentCount;
    }

    public long getParameterCount() {
        return parameterCount;
    }

    public void setParameterCount(long parameterCount) {
        this.parameterCount = parameterCount;
    }

    public Universe getUniverse() {
        return universe;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }
}
