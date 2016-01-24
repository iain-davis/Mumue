package org.mumue.mumue;

public interface Repository<T> {
    T get(long id);
    void add(T object);
    void save(T object);
}
