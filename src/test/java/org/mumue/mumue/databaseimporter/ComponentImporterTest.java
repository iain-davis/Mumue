package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.character.GameCharacter;
import org.mumue.mumue.components.space.Space;

public class ComponentImporterTest {
    private final ComponentImporter importer = new ComponentImporter();

    @Test
    public void neverReturnNull() {
        List<Component> components = importer.importFrom(ImportTestHelper.generateOneComponent(FuzzballDatabaseItemType.ROOM));

        assertThat(components, notNullValue());
    }

    @Test
    public void importOneCharacterComponent() {
        List<Component> components = importer.importFrom(ImportTestHelper.generateOneComponent(FuzzballDatabaseItemType.CHARACTER));

        assertThat(components, notNullValue());
        assertThat(components.size(), equalTo(1));
        assertThat(components.get(0), instanceOf(GameCharacter.class));
    }

    @Test
    public void importOneSpaceComponent() {
        List<Component> components = importer.importFrom(ImportTestHelper.generateOneComponent(FuzzballDatabaseItemType.ROOM));

        assertThat(components, notNullValue());
        assertThat(components.size(), equalTo(1));
        assertThat(components.get(0), instanceOf(Space.class));
    }

    @Test
    public void importTwoComponents() {
        List<String> lines = new ArrayList<>();
        lines.addAll(ImportTestHelper.generateOneComponent(FuzzballDatabaseItemType.ROOM));
        lines.addAll(ImportTestHelper.generateOneComponent(FuzzballDatabaseItemType.CHARACTER));

        List<Component> components = importer.importFrom(lines);

        assertThat(components, notNullValue());
        assertThat(components.size(), equalTo(2));
        assertThat(components, hasItem(isA(Space.class)));
        assertThat(components, hasItem(isA(GameCharacter.class)));
    }
}