package org.ruhlendavis.meta.importer.stages;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import org.ruhlendavis.meta.components.Artifact;
import org.ruhlendavis.meta.components.Component;
import org.ruhlendavis.meta.components.GameCharacter;
import org.ruhlendavis.meta.components.Link;
import org.ruhlendavis.meta.components.Program;
import org.ruhlendavis.meta.components.Space;
import org.ruhlendavis.meta.importer.ImportBucket;
import org.ruhlendavis.meta.importer.ImporterStageTestHelper;

public class LinkSourceChainStageTest extends ImporterStageTestHelper {
    private LinkSourceChainStage stage = new LinkSourceChainStage();

    @Test
    public void spaceHasMultipleLinks() {
        ImportBucket bucket = new ImportBucket();

        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        bucket.getComponents().put(space.getReference(), space);
        List<String> lines = setupLines(space);
        bucket.getComponentLines().put(space.getReference(), lines);

        Link firstLink = new Link().withId(RandomUtils.nextLong(100, 200));
        space.getLinks().add(firstLink);
        lines = setupLines(firstLink);
        bucket.getComponents().put(firstLink.getReference(), firstLink);
        bucket.getComponentLines().put(firstLink.getReference(), lines);

        List<Link> links = new ArrayList<>();
        links.add(firstLink);
        for (int i = 0; i < 3; i++) {
            Link link = new Link().withId(200L + i);
            links.add(link);
            lines.set(4, link.getReference().toString());
            lines = setupLines(link);
            bucket.getComponents().put(link.getReference(), link);
            bucket.getComponentLines().put(link.getReference(), lines);
        }
        bucket.getComponentLines().get(links.get(links.size() -1).getReference()).set(4, "-1");

        stage.run(bucket);

        assertEquals(4, space.getLinks().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(links.get(i).getReference(), space.getLinks().get(i).getReference());
        }
    }

    @Test
    public void spaceHasOneLink() {
        ImportBucket bucket = new ImportBucket();

        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        bucket.getComponents().put(space.getReference(), space);
        List<String> lines = setupLines(space);
        bucket.getComponentLines().put(space.getReference(), lines);

        Link firstLink = new Link().withId(RandomUtils.nextLong(100, 200));
        space.getLinks().add(firstLink);
        lines = setupLines(firstLink);
        bucket.getComponents().put(firstLink.getReference(), firstLink);
        bucket.getComponentLines().put(firstLink.getReference(), lines);

        stage.run(bucket);

        assertEquals(1, space.getLinks().size());
        assertEquals(firstLink.getReference(), space.getLinks().get(0).getReference());
    }

    @Test
    public void spaceHasNoLinks() {
        ImportBucket bucket = new ImportBucket();

        Space space = new Space().withId(RandomUtils.nextLong(2, 100));
        bucket.getComponents().put(space.getReference(), space);
        List<String> lines = setupLines(space);
        bucket.getComponentLines().put(space.getReference(), lines);

        stage.run(bucket);

        assertEquals(0, space.getLinks().size());
    }

    @Test
    public void artifactHasMultipleLinks() {
        ImportBucket bucket = new ImportBucket();

        Artifact artifact = new Artifact().withId(RandomUtils.nextLong(2, 100));
        bucket.getComponents().put(artifact.getReference(), artifact);
        List<String> lines = setupLines(artifact);
        bucket.getComponentLines().put(artifact.getReference(), lines);

        Link firstLink = new Link().withId(RandomUtils.nextLong(100, 200));
        artifact.getLinks().add(firstLink);
        lines = setupLines(firstLink);
        bucket.getComponents().put(firstLink.getReference(), firstLink);
        bucket.getComponentLines().put(firstLink.getReference(), lines);

        List<Link> links = new ArrayList<>();
        links.add(firstLink);
        for (int i = 0; i < 3; i++) {
            Link link = new Link().withId(200L + i);
            links.add(link);
            lines.set(4, link.getReference().toString());
            lines = setupLines(link);
            bucket.getComponents().put(link.getReference(), link);
            bucket.getComponentLines().put(link.getReference(), lines);
        }
        bucket.getComponentLines().get(links.get(links.size() -1).getReference()).set(4, "-1");

        stage.run(bucket);

        assertEquals(4, artifact.getLinks().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(links.get(i).getReference(), artifact.getLinks().get(i).getReference());
        }
    }

    @Test
    public void characterHasMultipleLinks() {
        ImportBucket bucket = new ImportBucket();

        GameCharacter character = new GameCharacter().withId(RandomUtils.nextLong(2, 100));
        bucket.getComponents().put(character.getReference(), character);
        List<String> lines = setupLines(character);
        bucket.getComponentLines().put(character.getReference(), lines);

        Link firstLink = new Link().withId(RandomUtils.nextLong(100, 200));
        character.getLinks().add(firstLink);
        lines = setupLines(firstLink);
        bucket.getComponents().put(firstLink.getReference(), firstLink);
        bucket.getComponentLines().put(firstLink.getReference(), lines);

        List<Link> links = new ArrayList<>();
        links.add(firstLink);
        for (int i = 0; i < 3; i++) {
            Link link = new Link().withId(200L + i);
            links.add(link);
            lines.set(4, link.getReference().toString());
            lines = setupLines(link);
            bucket.getComponents().put(link.getReference(), link);
            bucket.getComponentLines().put(link.getReference(), lines);
        }
        bucket.getComponentLines().get(links.get(links.size() -1).getReference()).set(4, "-1");

        stage.run(bucket);

        assertEquals(4, character.getLinks().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(links.get(i).getReference(), character.getLinks().get(i).getReference());
        }
    }

    private List<String> setupLines(Component component) {
        List<String> lines = new ArrayList<>();
        lines.add("#" + component.getReference().toString());
        lines.add(RandomStringUtils.randomAlphabetic(13));
        lines.add("-1");
        lines.add("-1");
        lines.add("-1");
        lines.add("0 0");
        lines.add("0");
        lines.add("0");
        lines.add("0");
        lines.add("0");
        lines.add("*Props*");
        lines.add("_/de:10:description");
        lines.add("*End*");
        if (component instanceof Program) {
            lines.add("1");
        } else if (component instanceof GameCharacter) {
            lines.add("0");
            lines.add("-1");
            lines.add("0");
            lines.add("password");
        } else if (component instanceof Link) {
            lines.add("0");
            lines.add("1");
        } else if (component instanceof Space) {
            lines.add("-1");
            lines.add("-1");
            lines.add("1");
        } else if (component instanceof Artifact) {
            lines.add("0");
            lines.add("-1");
            lines.add("1");
            lines.add("0");
        }
        return lines;
    }
}
