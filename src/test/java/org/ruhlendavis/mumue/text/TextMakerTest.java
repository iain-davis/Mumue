package org.ruhlendavis.mumue.text;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TextMakerTest {
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
    public void getFallsBackOnAlternateLocaleWhenNotFoundTextDaoReturnsNull() {
        String locale = RandomStringUtils.randomAlphabetic(5);
        String alternateLocale = RandomStringUtils.randomAlphabetic(4);
        String text = RandomStringUtils.randomAlphabetic(50);
        when(textDao.getText(TextName.Welcome, locale)).thenReturn(null);
        when(textDao.getText(TextName.Welcome, alternateLocale)).thenReturn(text);

        assertThat(textMaker.getText(TextName.Welcome, locale, alternateLocale), equalTo(text));
    }

    @Test
    public void getFallsBackOnAlternateLocaleWhenTextDaoReturnsBlank() {
        String locale = RandomStringUtils.randomAlphabetic(5);
        String alternateLocale = RandomStringUtils.randomAlphabetic(4);
        String text = RandomStringUtils.randomAlphabetic(50);
        when(textDao.getText(TextName.Welcome, locale)).thenReturn("");
        when(textDao.getText(TextName.Welcome, alternateLocale)).thenReturn(text);

        assertThat(textMaker.getText(TextName.Welcome, locale, alternateLocale), equalTo(text));
    }

    @Test
    public void replaceSlashNWithNewLine() {
        String locale = RandomStringUtils.randomAlphabetic(5);
        String text = RandomStringUtils.randomAlphabetic(27);
        when(textDao.getText(TextName.Welcome, locale)).thenReturn(text + "\\n");
        String expected = text + "\n";
        assertThat(textMaker.getText(TextName.Welcome, locale), equalTo(expected));
    }

    @Test
    public void replaceSlashRWithCarriageReturn() {
        String locale = RandomStringUtils.randomAlphabetic(5);
        String text = RandomStringUtils.randomAlphabetic(27);
        when(textDao.getText(TextName.Welcome, locale)).thenReturn(text + "\\r");
        String expected = text + "\r";
        assertThat(textMaker.getText(TextName.Welcome, locale), equalTo(expected));
    }
}
