package ru.kitelev.willey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LruCacheTest {

    private LruCache<Integer, Integer> cache;

    @BeforeEach
    void setUp() {
        cache = new LruCache<>(3);
    }

    @Test
    void savesKeyValues() {
        cache.set(1, 10);
        cache.set(2, 20);
        assertEquals(10, cache.get(1));
        assertEquals(20, cache.get(2));
    }

    @Test
    void removesLastUsedElements() {
        cache.set(1, 10);
        cache.set(2, 20);
        cache.set(3, 30);

        assertEquals(List.of(1, 2, 3), cache.keysList());

        cache.get(1);
        cache.set(4, 40);
        assertNull(cache.get(2));

        assertEquals(List.of(3, 1, 4), cache.keysList());
    }
}

