package org.mumue.mumue.text;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TextMakerTest {
    private final TextDao textDao = mock(TextDao.class);
    private final TextMaker textMaker = new TextMaker(textDao);

    @Test
    void getReturnsText() {
        String locale = RandomStringUtils.insecure().nextAlphabetic(5);
        String text = RandomStringUtils.insecure().nextAlphabetic(257);
        when(textDao.getText(TextName.Welcome, locale)).thenReturn(text);
        assertThat(textMaker.getText(TextName.Welcome, locale), equalTo(text));
    }

    @Test
    void getFallsBackOnTextNameWhenTextDaoReturnsNull() {
        String otherLocale = RandomStringUtils.insecure().nextAlphabetic(4);
        when(textMaker.getText(TextName.Welcome, otherLocale)).thenReturn(null);
        assertThat(textMaker.getText(TextName.Welcome, otherLocale), equalTo(TextName.Welcome.toString()));
    }

    @Test
    void getFallsBackOnTextNameWhenTextDaoReturnsBlank() {
        String otherLocale = RandomStringUtils.insecure().nextAlphabetic(4);
        when(textMaker.getText(TextName.Welcome, otherLocale)).thenReturn("");
        assertThat(textMaker.getText(TextName.Welcome, otherLocale), equalTo(TextName.Welcome.toString()));
    }

    @Test
    void performVariableReplacement() {
        String locale = RandomStringUtils.insecure().nextAlphabetic(5);
        String textL = RandomStringUtils.insecure().nextAlphabetic(25);
        String textR = RandomStringUtils.insecure().nextAlphabetic(25);
        String variable = RandomStringUtils.insecure().nextAlphabetic(7);
        String replacement = RandomStringUtils.insecure().nextAlphabetic(15);
        Map<String, String> variables = new HashMap<>();
        variables.put(variable, replacement);

        String text = textL + "${" + variable + "}" + textR;

        String expected = textL + replacement + textR;
        when(textDao.getText(TextName.Welcome, locale)).thenReturn(text);
        assertThat(textMaker.getText(TextName.Welcome, locale, variables), equalTo(expected));
    }
}
