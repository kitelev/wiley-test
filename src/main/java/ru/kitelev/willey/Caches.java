package ru.kitelev.willey;

public class Caches {
    public static <K, V> Cache<K, V> create(int maxSize, Strategy strategy) {
        switch (strategy) {
            case LFU:
                return new LfuCache<>(maxSize);
            case LRU:
                return new LruCache<>(maxSize);
            default:
                throw new IllegalArgumentException("Unknown cache strategy: " + strategy);
        }
    }

    enum Strategy {
        LRU,
        LFU
    }
}
