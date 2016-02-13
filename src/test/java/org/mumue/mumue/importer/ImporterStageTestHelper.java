package org.mumue.mumue.importer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.mumue.mumue.importer.components.Artifact;
import org.mumue.mumue.importer.components.Component;
import org.mumue.mumue.importer.components.GameCharacter;
import org.mumue.mumue.importer.components.Homeable;
import org.mumue.mumue.importer.components.Link;
import org.mumue.mumue.importer.components.LinkSource;
import org.mumue.mumue.importer.components.Ownable;
import org.mumue.mumue.importer.components.Program;
import org.mumue.mumue.importer.components.Space;

public class ImporterStageTestHelper {
    protected void addOneDatabaseItemToList(List<String> list, String id, String flags, int propLines, int codaLines) {
        addOneDatabaseItemToList(list, id, flags, propLines);
        addRandomLinesToList(list, codaLines);
    }

    protected void addOneDatabaseItemToList(List<String> list, String id, String flags, int propLines) {
        list.add("#" + id);
        addRandomLinesToList(list, 4);
        list.add(flags);
        addRandomLinesToList(list, 4);
        list.add("*Props*");
        addRandomLinesToList(list, propLines);
        list.add("*End*");
    }

    protected void addRandomLinesToList(List<String> list, int count) {
        for (int i = 0; i < count; i++) {
            list.add(RandomStringUtils.randomAlphabetic(5));
        }
    }

    protected List<String> mockComponentLines(Component component) {
        List<String> lines = new ArrayList<>();
        lines.add("#" + component.getId().toString());
        lines.add(component.getName());
        if (component.getLocation() != null) {
            lines.add(component.getLocation().getId().toString());
        }
        if (component.getContents().size() == 0) {
            lines.add("-1");
        } else {
            lines.add(component.getContents().get(0).getId().toString());
        }
        lines.add("-1"); // Next
        addFlagLine(component, lines);
        lines.add(String.valueOf(component.getCreated().getEpochSecond()));
        lines.add(String.valueOf(component.getLastUsed().getEpochSecond()));
        lines.add(String.valueOf(component.getUseCount()));
        lines.add(String.valueOf(component.getLastModified().getEpochSecond()));
        lines.add("*Props*");
        lines.add("_/de:10:" + component.getDescription());
        lines.add("*End*");
        if (component instanceof Program) {
            addOwner((Ownable) component, lines);
        } else if (component instanceof GameCharacter) {
            lines.add(((Homeable) component).getHome().getId().toString());
            if (((LinkSource) component).getLinks().size() == 0) {
                lines.add("-1");
            } else {
                lines.add(((LinkSource) component).getLinks().get(0).getId().toString());
            }
            lines.add(((GameCharacter) component).getWealth().toString());
            lines.add("password");
        } else if (component instanceof Link) {
            lines.add(String.valueOf(((Link)component).getDestinations().size()));
            for (Component destination : ((Link)component).getDestinations()) {
                lines.add(destination.getId().toString());
            }
            addOwner((Ownable) component, lines);
        } else if (component instanceof Space) {
            lines.add("-1");
            lines.add("-1");
            addOwner((Ownable) component, lines);
        } else if (component instanceof Artifact) {
            lines.add(((Homeable) component).getHome().getId().toString());
            if (((LinkSource) component).getLinks().size() == 0) {
                lines.add("-1");
            } else {
                lines.add(((LinkSource) component).getLinks().get(0).getId().toString());
            }
            addOwner((Ownable) component, lines);
            lines.add(String.valueOf(((Artifact)component).getValue().toString()));
        }
        return lines;
    }

    private void addOwner(Ownable component, List<String> lines) {
        if (component.getOwner() == null) {
            lines.add("1");
        } else {
            lines.add(component.getOwner().getId().toString());
        }
    }

    private void addFlagLine(Component component, List<String> lines) {
        if (component instanceof Program) {
            lines.add("4");
        } else if (component instanceof GameCharacter) {
            lines.add("3");
        } else if (component instanceof Link) {
            lines.add("2");
        } else if (component instanceof Space) {
            lines.add("0");
        } else if (component instanceof Artifact) {
            lines.add("1");
        }
    }
}
