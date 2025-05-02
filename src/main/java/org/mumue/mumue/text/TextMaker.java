package org.mumue.mumue.text;

import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class TextMaker {
    public static final String TEXT_VARIABLE_LOGIN_ID = "loginId";
    private final TextDao textDao;

    @Inject
    public TextMaker(TextDao textDao) {
        this.textDao = textDao;
    }

    public String getText(TextName textName, String locale) {
        String text = textDao.getText(textName, locale);
        if (StringUtils.isBlank(text)) {
            return textName.toString();
        }
        return text;
    }

    public String getText(TextName textName, String locale, Map<String, String> variables) {
        String text = getText(textName, locale);
        return new StringSubstitutor(variables).replace(text);
    }
}
