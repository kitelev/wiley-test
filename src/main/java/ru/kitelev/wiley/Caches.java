package ru.kitelev.wiley;

public class Caches {
    public static <K, V> Cache<K, V> create(int capacity, Strategy strategy) {
        switch (strategy) {
            case LFU:
                return new LfuCache<>(capacity);
            case LRU:
                return new LruCache<>(capacity);
            default:
                throw new IllegalArgumentException("Unknown cache strategy: " + strategy);
        }
    }

    enum Strategy {
        LRU,
        LFU
    }
}
