package ru.kitelev.wiley;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * LRU (least recently used) cache implementation
 *
 * @param <K> key type
 * @param <V> value type
 * @implNote It is just a {@link LinkedHashMap} wrapping with overriding method for deletion the eldest entry
 */
public class LruCache<K, V> implements Cache<K, V> {
    private final LinkedHashMap<K, V> map;

    public LruCache(int capacity) {
        this.map = new LinkedHashMap<>(capacity + 1, 1, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > capacity;
            }
        };
    }

    @Override
    public void set(K key, V value) {
        map.put(key, value);
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    /**
     * Ordered keys list. Last one is the last used
     */
    protected List<K> keysList() {
        return new ArrayList<>(map.keySet());
    }
}
