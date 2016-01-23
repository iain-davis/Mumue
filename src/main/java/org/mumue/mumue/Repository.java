package org.mumue.mumue;

public interface Repository<T> {
    T get(long id);
    void save(T object);
}
