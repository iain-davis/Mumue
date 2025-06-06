package org.mumue.mumue.importer.stages;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.mumue.mumue.importer.ImportBucket;
import org.mumue.mumue.importer.ImporterStageTestHelper;
import org.mumue.mumue.importer.components.Artifact;
import org.mumue.mumue.importer.components.Component;
import org.mumue.mumue.importer.components.GameCharacter;
import org.mumue.mumue.importer.components.Link;
import org.mumue.mumue.importer.components.Program;
import org.mumue.mumue.importer.components.Space;

public class LinkSourceChainStageTest extends ImporterStageTestHelper {
    private LinkSourceChainStage stage = new LinkSourceChainStage();

    @Test
    public void spaceHasMultipleLinks() {
        ImportBucket bucket = new ImportBucket();

        Space space = new Space().withId(RandomUtils.insecure().randomLong(2, 100));
        bucket.getComponents().put(space.getId(), space);
        List<String> lines = setupLines(space);
        bucket.getComponentLines().put(space.getId(), lines);

        Link firstLink = new Link().withId(RandomUtils.insecure().randomLong(100, 200));
        space.getLinks().add(firstLink);
        lines = setupLines(firstLink);
        bucket.getComponents().put(firstLink.getId(), firstLink);
        bucket.getComponentLines().put(firstLink.getId(), lines);

        List<Link> links = new ArrayList<>();
        links.add(firstLink);
        for (int i = 0; i < 3; i++) {
            Link link = new Link().withId(200L + i);
            links.add(link);
            lines.set(4, link.getId().toString());
            lines = setupLines(link);
            bucket.getComponents().put(link.getId(), link);
            bucket.getComponentLines().put(link.getId(), lines);
        }
        bucket.getComponentLines().get(links.get(links.size() -1).getId()).set(4, "-1");

        stage.run(bucket);

        assertEquals(4, space.getLinks().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(links.get(i).getId(), space.getLinks().get(i).getId());
        }
    }

    @Test
    public void spaceHasOneLink() {
        ImportBucket bucket = new ImportBucket();

        Space space = new Space().withId(RandomUtils.insecure().randomLong(2, 100));
        bucket.getComponents().put(space.getId(), space);
        List<String> lines = setupLines(space);
        bucket.getComponentLines().put(space.getId(), lines);

        Link firstLink = new Link().withId(RandomUtils.insecure().randomLong(100, 200));
        space.getLinks().add(firstLink);
        lines = setupLines(firstLink);
        bucket.getComponents().put(firstLink.getId(), firstLink);
        bucket.getComponentLines().put(firstLink.getId(), lines);

        stage.run(bucket);

        assertEquals(1, space.getLinks().size());
        assertEquals(firstLink.getId(), space.getLinks().get(0).getId());
    }

    @Test
    public void spaceHasNoLinks() {
        ImportBucket bucket = new ImportBucket();

        Space space = new Space().withId(RandomUtils.insecure().randomLong(2, 100));
        bucket.getComponents().put(space.getId(), space);
        List<String> lines = setupLines(space);
        bucket.getComponentLines().put(space.getId(), lines);

        stage.run(bucket);

        assertEquals(0, space.getLinks().size());
    }

    @Test
    public void artifactHasMultipleLinks() {
        ImportBucket bucket = new ImportBucket();

        Artifact artifact = new Artifact().withId(RandomUtils.insecure().randomLong(2, 100));
        bucket.getComponents().put(artifact.getId(), artifact);
        List<String> lines = setupLines(artifact);
        bucket.getComponentLines().put(artifact.getId(), lines);

        Link firstLink = new Link().withId(RandomUtils.insecure().randomLong(100, 200));
        artifact.getLinks().add(firstLink);
        lines = setupLines(firstLink);
        bucket.getComponents().put(firstLink.getId(), firstLink);
        bucket.getComponentLines().put(firstLink.getId(), lines);

        List<Link> links = new ArrayList<>();
        links.add(firstLink);
        for (int i = 0; i < 3; i++) {
            Link link = new Link().withId(200L + i);
            links.add(link);
            lines.set(4, link.getId().toString());
            lines = setupLines(link);
            bucket.getComponents().put(link.getId(), link);
            bucket.getComponentLines().put(link.getId(), lines);
        }
        bucket.getComponentLines().get(links.get(links.size() -1).getId()).set(4, "-1");

        stage.run(bucket);

        assertEquals(4, artifact.getLinks().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(links.get(i).getId(), artifact.getLinks().get(i).getId());
        }
    }

    @Test
    public void characterHasMultipleLinks() {
        ImportBucket bucket = new ImportBucket();

        GameCharacter character = new GameCharacter().withId(RandomUtils.insecure().randomLong(2, 100));
        bucket.getComponents().put(character.getId(), character);
        List<String> lines = setupLines(character);
        bucket.getComponentLines().put(character.getId(), lines);

        Link firstLink = new Link().withId(RandomUtils.insecure().randomLong(100, 200));
        character.getLinks().add(firstLink);
        lines = setupLines(firstLink);
        bucket.getComponents().put(firstLink.getId(), firstLink);
        bucket.getComponentLines().put(firstLink.getId(), lines);

        List<Link> links = new ArrayList<>();
        links.add(firstLink);
        for (int i = 0; i < 3; i++) {
            Link link = new Link().withId(200L + i);
            links.add(link);
            lines.set(4, link.getId().toString());
            lines = setupLines(link);
            bucket.getComponents().put(link.getId(), link);
            bucket.getComponentLines().put(link.getId(), lines);
        }
        bucket.getComponentLines().get(links.get(links.size() -1).getId()).set(4, "-1");

        stage.run(bucket);

        assertEquals(4, character.getLinks().size());
        for (int i = 0; i < 4; i++) {
            assertEquals(links.get(i).getId(), character.getLinks().get(i).getId());
        }
    }

    private List<String> setupLines(Component component) {
        List<String> lines = new ArrayList<>();
        lines.add("#" + component.getId().toString());
        lines.add(RandomStringUtils.insecure().nextAlphabetic(13));
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
