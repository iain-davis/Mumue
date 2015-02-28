package org.ruhlendavis.mumue.connection.stages.mainmenu;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.ruhlendavis.mumue.components.CharacterDao;
import org.ruhlendavis.mumue.components.GameCharacter;
import org.ruhlendavis.mumue.configuration.Configuration;
import org.ruhlendavis.mumue.connection.Connection;
import org.ruhlendavis.mumue.connection.stages.ConnectionStage;
import org.ruhlendavis.mumue.player.Player;
import org.ruhlendavis.mumue.text.TextMaker;
import org.ruhlendavis.mumue.text.TextName;

@RunWith(MockitoJUnitRunner.class)
public class CharacterSelectionPromptTest {
    private final String prompt = RandomStringUtils.randomAlphanumeric(17);
    private final String locale = RandomStringUtils.randomAlphabetic(15);
    private final String serverLocale = RandomStringUtils.randomAlphabetic(5);
    private final Player player = new Player().withLocale(locale).withLoginId(RandomStringUtils.randomAlphabetic(7));
    private final Connection connection = new Connection().withPlayer(player);

    @Mock CharacterDao dao;
    @Mock Configuration configuration;
    @Mock TextMaker textMaker;
    @InjectMocks CharacterSelectionPrompt stage;

    @Before
    public void beforeEach() {
        when(configuration.getServerLocale()).thenReturn(serverLocale);
        when(textMaker.getText(TextName.CharacterSelectionPrompt, locale, serverLocale)).thenReturn(prompt);
        when(dao.getCharacters(player.getId())).thenReturn(new ArrayList<>());
    }

    @Test
    public void returnWaitForCharacterSelection() {
        ConnectionStage next = stage.execute(connection, configuration);
        assertNotNull(next);
        assertThat(next, instanceOf(WaitForCharacterSelection.class));
    }

    @Test
    public void putPromptOnOutputQueue() {
        stage.execute(connection, configuration);

        assertThat(connection.getOutputQueue(), hasItem(prompt));
    }

    @Test
    public void displayCharacterList() {
        String name1 = RandomStringUtils.randomAlphabetic(17);
        String name2 = RandomStringUtils.randomAlphabetic(16);
        List<GameCharacter> characters = setupCharacters(name1, name2);
        when(dao.getCharacters(player.getId())).thenReturn(characters);

        stage.execute(connection, configuration);

        String expected = "\\r\\n1) " + name1 + "\\r\\n2) " + name2 + "\\r\\n\\r\\n";
        assertThat(connection.getOutputQueue(), hasItem(expected));
    }

    @Test
    public void storeCharacterIdsByMenuOption() {
        long id1 = RandomUtils.nextLong(100, 200);
        long id2 = RandomUtils.nextLong(200, 300);
        List<GameCharacter> characters = setupCharacters(id1, id2);
        when(dao.getCharacters(player.getId())).thenReturn(characters);

        stage.execute(connection, configuration);

        assertThat(connection.getMenuOptionIds().values(), hasItem(id1));
        assertThat(connection.getMenuOptionIds().values(), hasItem(id2));
    }

    private List<GameCharacter> setupCharacters(long id1, long id2) {
        return setupCharacters(id1, id2, RandomStringUtils.randomAlphabetic(7), RandomStringUtils.randomAlphabetic(8));
    }

    private List<GameCharacter> setupCharacters(String name1, String name2) {
        return setupCharacters(RandomUtils.nextLong(1, 100), RandomUtils.nextLong(100, 200), name1, name2);
    }

    private List<GameCharacter> setupCharacters(long id1, long id2, String name1, String name2) {
        GameCharacter character1 = new GameCharacter().withName(name1).withId(id1);
        GameCharacter character2 = new GameCharacter().withName(name2).withId(id2);
        List<GameCharacter> characters = new ArrayList<>();
        characters.add(character1);
        characters.add(character2);
        return characters;
    }
}
