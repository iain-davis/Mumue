package org.mumue.mumue.text;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TextQueue implements Iterable<String> {
    private final Collection<String> texts = new ConcurrentLinkedQueue<>();

    public void push(String text) {
        texts.add(text);
    }

    public String peek() {
        return texts.stream().findFirst().get();
    }

    public String pop() {
        String text = texts.stream().findFirst().get();
        texts.remove(text);
        return text;
    }

    public boolean hasAny() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return texts.isEmpty();
    }

    public int size() {
        return texts.size();
    }

    @Override
    public Iterator<String> iterator() {
        return texts.iterator();
    }
}
