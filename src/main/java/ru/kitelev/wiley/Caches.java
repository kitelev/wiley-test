package ru.kitelev.wiley;

import ru.kitelev.wiley.impl.LfuCache;
import ru.kitelev.wiley.impl.LruCache;

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
