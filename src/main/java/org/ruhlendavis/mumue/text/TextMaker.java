package org.ruhlendavis.mumue.text;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;

public class TextMaker {
    private TextDao textDao = new TextDao();

    public String getText(TextName textName, String locale) {
        String text = textDao.getText(textName, locale);
        if (StringUtils.isBlank(text)) {
            return textName.toString();
        }
        return text;
    }

    public String getText(TextName textName, String locale, Map<String, String> variables) {
        String text = getText(textName, locale);
        StrSubstitutor substitutor = new StrSubstitutor(variables);
        return substitutor.replace(text);
    }
}
