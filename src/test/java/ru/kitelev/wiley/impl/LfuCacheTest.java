package ru.kitelev.wiley.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LfuCacheTest {

    private LfuCache<Integer, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new LfuCache<>(3);
    }

    @Test
    void savesValues() {
        cache.set(1, 10);
        cache.set(2, 20);
        assertEquals(10, cache.get(1));
        assertEquals(20, cache.get(2));
    }

    @Test
    void removesLeastFrequentlyUsedValues() {
        cache.set(1, 10);
        cache.set(2, 20);
        cache.set(3, 30);

        cache.get(1);
        cache.get(1);
        cache.get(3);
        cache.set(4, 40);

        assertNotNull(cache.get(1));
        assertNull(cache.get(2));
        assertNotNull(cache.get(3));
        assertNotNull(cache.get(4));

        cache.get(3);
        cache.get(3);
        cache.get(3);
        cache.get(4);
        cache.get(4);
        cache.get(4);

        cache.set(5, 50);
        assertNotNull(cache.get(3));
        assertNotNull(cache.get(4));
        assertNotNull(cache.get(5));
    }
}
