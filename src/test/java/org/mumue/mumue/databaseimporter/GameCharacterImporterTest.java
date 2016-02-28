package org.mumue.mumue.databaseimporter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

import org.junit.Test;
import org.mumue.mumue.components.Component;
import org.mumue.mumue.components.character.GameCharacter;

public class GameCharacterImporterTest {
    private final GameCharacterImporter importer = new GameCharacterImporter();

    @Test
    public void createComponent() {
        Component component = importer.createComponent();

        assertThat(component, instanceOf(GameCharacter.class));
    }
}