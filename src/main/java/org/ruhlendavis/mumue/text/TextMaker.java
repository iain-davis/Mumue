package org.ruhlendavis.mumue.text;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

public class TextMaker {
    private TextDao textDao = new TextDao();

    public String getText(TextName textName, String locale) {
        return getText(textName, locale, "");
    }

    public String getText(TextName textName, String locale, String alternateLocale) {
        return getText(textName, locale, alternateLocale, new HashMap<>());
    }

    public String getText(TextName textName, String locale, String alternateLocale, Map<String, String> variables) {
        String text = getTextFor(textName, locale, alternateLocale);
        StrSubstitutor substitutor = new StrSubstitutor(variables);
        return substitutor.replace(text);
    }

    private String getTextFor(TextName textName, String locale, String alternateLocale) {
        String text = textDao.getText(textName, locale);
        if (StringUtils.isBlank(text)) {
            if (StringUtils.isBlank(alternateLocale)) {
                return textName.toString();
            }
            text = textDao.getText(textName, alternateLocale);
        }
        return text;
    }
}
