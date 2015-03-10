package org.ruhlendavis.mumue.text;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class TextMakerTest {
    @Rule public MockitoRule mockito = MockitoJUnit.rule();
    @Mock TextDao textDao;
    @InjectMocks TextMaker textMaker;

    @Test
    public void getReturnsText() {
        String locale = RandomStringUtils.randomAlphabetic(5);
        String text = RandomStringUtils.randomAlphabetic(257);
        when(textDao.getText(TextName.Welcome, locale)).thenReturn(text);
        assertThat(textMaker.getText(TextName.Welcome, locale), equalTo(text));
    }

    @Test
    public void getFallsBackOnTextNameWhenTextDaoReturnsNull() {
        String otherLocale = RandomStringUtils.randomAlphabetic(4);
        when(textMaker.getText(TextName.Welcome, otherLocale)).thenReturn(null);
        assertThat(textMaker.getText(TextName.Welcome, otherLocale), equalTo(TextName.Welcome.toString()));
    }

    @Test
    public void getFallsBackOnTextNameWhenTextDaoReturnsBlank() {
        String otherLocale = RandomStringUtils.randomAlphabetic(4);
        when(textMaker.getText(TextName.Welcome, otherLocale)).thenReturn("");
        assertThat(textMaker.getText(TextName.Welcome, otherLocale), equalTo(TextName.Welcome.toString()));
    }

    @Test
    public void performVariableReplacment() {
        String locale = RandomStringUtils.randomAlphabetic(5);
        String textL = RandomStringUtils.randomAlphabetic(25);
        String textR = RandomStringUtils.randomAlphabetic(25);
        String variable = RandomStringUtils.randomAlphabetic(7);
        String replacement = RandomStringUtils.randomAlphabetic(15);
        Map<String, String> variables = new HashMap<>();
        variables.put(variable, replacement);

        String text = textL + "${" + variable + "}" + textR;

        String expected = textL + replacement + textR;
        when(textDao.getText(TextName.Welcome, locale)).thenReturn(text);
        assertThat(textMaker.getText(TextName.Welcome, locale, variables), equalTo(expected));
    }
}
