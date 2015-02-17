package org.ruhlendavis.meta.importer;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;

import org.ruhlendavis.meta.componentsold.Artifact;
import org.ruhlendavis.meta.componentsold.Component;
import org.ruhlendavis.meta.componentsold.GameCharacter;
import org.ruhlendavis.meta.componentsold.Homeable;
import org.ruhlendavis.meta.componentsold.Link;
import org.ruhlendavis.meta.componentsold.LinkSource;
import org.ruhlendavis.meta.componentsold.Ownable;
import org.ruhlendavis.meta.componentsold.Program;
import org.ruhlendavis.meta.componentsold.Space;
import org.ruhlendavis.meta.player.Player;

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

    protected List<String> mockComponentLines(Component component, Player player) {
        List<String> lines = new ArrayList<>();
        lines.add("#" + component.getReference().toString());
        lines.add(component.getName());
        if (component.getLocation() != null) {
            lines.add(component.getLocation().getReference().toString());
        }
        if (component.getContents().size() == 0) {
            lines.add("-1");
        } else {
            lines.add(component.getContents().get(0).getReference().toString());
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
            lines.add(((Homeable) component).getHome().getReference().toString());
            if (((LinkSource) component).getLinks().size() == 0) {
                lines.add("-1");
            } else {
                lines.add(((LinkSource) component).getLinks().get(0).getReference().toString());
            }
            lines.add(((GameCharacter) component).getWealth().toString());
            lines.add(player.getPassword());
        } else if (component instanceof Link) {
            lines.add(String.valueOf(((Link)component).getDestinations().size()));
            for (Component destination : ((Link)component).getDestinations()) {
                lines.add(destination.getReference().toString());
            }
            addOwner((Ownable) component, lines);
        } else if (component instanceof Space) {
            lines.add("-1");
            lines.add("-1");
            addOwner((Ownable) component, lines);
        } else if (component instanceof Artifact) {
            lines.add(((Homeable) component).getHome().getReference().toString());
            if (((LinkSource) component).getLinks().size() == 0) {
                lines.add("-1");
            } else {
                lines.add(((LinkSource) component).getLinks().get(0).getReference().toString());
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
            lines.add(component.getOwner().getReference().toString());
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
