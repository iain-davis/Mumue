package org.ruhlendavis.meta.text;

import org.apache.commons.lang3.StringUtils;

public class TextMaker {
    private TextDao textDao = new TextDao();

    public String getText(TextName textName, String locale) {
        return getText(textName, locale, locale);
    }

    public String getText(TextName textName, String locale, String alternateLocale) {
        String text = textDao.getText(textName, locale);
        if (StringUtils.isBlank(text)) {
            if (locale.equals(alternateLocale)) {
                return textName.toString();
            } else {
                return getText(textName, alternateLocale, alternateLocale);
            }
        }
        return text.replaceAll("\\\\n", "\n").replaceAll("\\\\r", "\r");
    }
}
