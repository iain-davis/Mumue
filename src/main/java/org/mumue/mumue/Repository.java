package org.mumue.mumue;

public interface Repository<T> {
    T get(long id);
    T get(Specification<T> specification);
    void save(T object);
}
