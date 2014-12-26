package org.ruhlendavis.meta.text;

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
        when(textDao.getText(locale, TextName.Welcome)).thenReturn(text);
        assertThat(textMaker.getText(locale, TextName.Welcome), equalTo(text));
    }

    @Test
    public void replaceSlashNWithNewLine() {
        String locale = RandomStringUtils.randomAlphabetic(5);
        String text = RandomStringUtils.randomAlphabetic(27);
        when(textDao.getText(locale, TextName.Welcome)).thenReturn(text + "\\n");
        String expected = text + "\n";
        assertThat(textMaker.getText(locale, TextName.Welcome), equalTo(expected));
    }

    @Test
    public void replaceSlashRWithCarriageReturn() {
        String locale = RandomStringUtils.randomAlphabetic(5);
        String text = RandomStringUtils.randomAlphabetic(27);
        when(textDao.getText(locale, TextName.Welcome)).thenReturn(text + "\\r");
        String expected = text + "\r";
        assertThat(textMaker.getText(locale, TextName.Welcome), equalTo(expected));
    }
}
