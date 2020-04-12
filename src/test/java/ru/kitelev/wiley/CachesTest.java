package ru.kitelev.wiley;

import org.junit.jupiter.api.Test;
import ru.kitelev.wiley.Caches.Strategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CachesTest {
    @Test
    void lfuCacheTest() {
        Cache<Integer, Integer> cache = Caches.create(3, Strategy.LFU);
        cache.set(1, 1);
        cache.get(1);
        cache.get(1);
        cache.get(1); // usage count for 1 is 3
        cache.set(2, 2);
        cache.get(2);  // usage count for 1 is 1
        cache.set(3, 3); // usage count for 3 is 0, so it will be removed
        cache.set(4, 4); // '4' will be added as new element

        assertNotNull(cache.get(1));
        assertNotNull(cache.get(2));
        assertNull(cache.get(3));
        assertNotNull(cache.get(4));
    }

    @Test
    void lruCacheTest() {
        Cache<Integer, Integer> cache = Caches.create(3, Strategy.LRU);
        cache.set(1, 1);
        cache.set(2, 2);
        cache.set(3, 3);
        cache.set(4, 4);

        assertNull(cache.get(1));
        assertNotNull(cache.get(2));
        assertNotNull(cache.get(3));
        assertNotNull(cache.get(4));

        cache.set(5, 5);

        assertNull(cache.get(2));
        assertNotNull(cache.get(3));
        assertNotNull(cache.get(4));
        assertNotNull(cache.get(5));
    }
}
