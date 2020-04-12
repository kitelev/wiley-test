package ru.kitelev.wiley;

public interface Cache<K, V> {
    V get(K key);

    void set(K key, V value);
}
