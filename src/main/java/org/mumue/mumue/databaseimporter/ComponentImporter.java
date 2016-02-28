package org.mumue.mumue.databaseimporter;

import java.util.List;

import org.mumue.mumue.components.Component;

interface ComponentImporter<T extends Component> {
    T importFrom(List<String> lines);
}
