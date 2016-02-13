package org.mumue.mumue.databaseimporter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.universe.Universe;

class ImportResults {
    private Universe universe = new Universe();
    private Collection<Component> components = new ArrayList<>();
    private Properties parameters = new Properties();

    public int getComponentCount() {
        return components.size();
    }

    public int getParameterCount() {
        return parameters.size();
    }

    public Universe getUniverse() {
        return universe;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    public Collection<Component> getComponents() {
        return components;
    }

    public void setComponents(Collection<Component> components) {
        this.components = components;
    }

    public void setParameters(Properties parameters) {
        this.parameters = parameters;
    }

    public Properties getParameters() {
        return parameters;
    }
}
