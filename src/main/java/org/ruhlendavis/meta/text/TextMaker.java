package org.ruhlendavis.meta.text;

public class TextMaker {
    private TextDao textDao = new TextDao();

    public String getText(String locale, TextName textName) {
        return textDao.getText(locale, textName).replaceAll("\\\\n", "\n").replaceAll("\\\\r", "\r");
    }
}
