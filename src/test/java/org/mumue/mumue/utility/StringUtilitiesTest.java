package org.mumue.mumue.utility;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

public class StringUtilitiesTest {
    private final StringUtilities utility = new StringUtilities();

    @Test
    public void returnEmptyStringForNullCollection() {
        assertThat(StringUtilities.commaIfy(null, ""), equalTo(""));
    }

    @Test
    public void returnEmptyStringForEmptyCollection() {
        assertThat(StringUtilities.commaIfy(new ArrayList<>(), ""), equalTo(""));
    }

    @Test
    public void returnOneString() {
        String string = RandomStringUtils.randomAlphabetic(17);
        Collection<String> stringCollection = new ArrayList<>(Collections.singletonList(string));

        assertThat(StringUtilities.commaIfy(stringCollection, ""), equalTo(string));
    }

    @Test
    public void returnTwoStrings() {
        String item1 = RandomStringUtils.randomAlphabetic(17);
        String item2 = RandomStringUtils.randomAlphabetic(18);
        String andString = RandomStringUtils.randomAlphabetic(3);
        Collection<String> stringCollection = new ArrayList<>(Arrays.asList(item1, item2));

        String expected = item1 + " " + andString + " " + item2;
        assertThat(StringUtilities.commaIfy(stringCollection, andString), equalTo(expected));
    }

    @Test
    public void returnThreeStrings() {
        String item1 = RandomStringUtils.randomAlphabetic(17);
        String item2 = RandomStringUtils.randomAlphabetic(18);
        String item3 = RandomStringUtils.randomAlphabetic(19);
        String andString = RandomStringUtils.randomAlphabetic(3);
        Collection<String> stringCollection = new ArrayList<>(Arrays.asList(item1, item2, item3));

        String expected = item1 + ", " + item2 + ", " + andString + " " + item3;
        assertThat(StringUtilities.commaIfy(stringCollection, andString), equalTo(expected));
    }
}
