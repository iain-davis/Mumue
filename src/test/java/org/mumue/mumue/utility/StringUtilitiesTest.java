package org.mumue.mumue.utility;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class StringUtilitiesTest {
    @Test
    void returnEmptyStringForNullCollection() {
        assertThat(StringUtilities.commaIfy(null, ""), equalTo(""));
    }

    @Test
    void returnEmptyStringForEmptyCollection() {
        assertThat(StringUtilities.commaIfy(new ArrayList<>(), ""), equalTo(""));
    }

    @Test
    void returnOneString() {
        String string = RandomStringUtils.insecure().nextAlphabetic(17);
        Collection<String> stringCollection = new ArrayList<>(Collections.singletonList(string));

        assertThat(StringUtilities.commaIfy(stringCollection, ""), equalTo(string));
    }

    @Test
    void returnTwoStrings() {
        String item1 = RandomStringUtils.insecure().nextAlphabetic(17);
        String item2 = RandomStringUtils.insecure().nextAlphabetic(18);
        String andString = RandomStringUtils.insecure().nextAlphabetic(3);
        Collection<String> stringCollection = new ArrayList<>(Arrays.asList(item1, item2));

        String expected = item1 + " " + andString + " " + item2;
        assertThat(StringUtilities.commaIfy(stringCollection, andString), equalTo(expected));
    }

    @Test
    void returnThreeStrings() {
        String item1 = RandomStringUtils.insecure().nextAlphabetic(17);
        String item2 = RandomStringUtils.insecure().nextAlphabetic(18);
        String item3 = RandomStringUtils.insecure().nextAlphabetic(19);
        String andString = RandomStringUtils.insecure().nextAlphabetic(3);
        Collection<String> stringCollection = new ArrayList<>(Arrays.asList(item1, item2, item3));

        String expected = item1 + ", " + item2 + ", " + andString + " " + item3;
        assertThat(StringUtilities.commaIfy(stringCollection, andString), equalTo(expected));
    }
}
