package ru.kitelev.willey;

import java.util.*;

/**
 * LFU (least frequently used) cache implementation with O(1) complexity
 *
 * @param <K> key type
 * @param <V> value type
 */
public class LfuCache<K, V> implements Cache<K, V> {

    private final int capacity;
    private final Map<K, V> map;
    private final Map<K, Integer> key2useCount;
    private final SortedMap<Integer, LinkedHashSet<K>> useCount2keys;

    public LfuCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity, 1);
        key2useCount = new HashMap<>(capacity, 1);
        useCount2keys = new TreeMap<>(Integer::compareTo);
    }

    /**
     * @param key key, not null
     * @return value for given key or {@code null} if cache does not contain it
     */
    @Override
    public V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        Integer oldCount = key2useCount.get(key);
        key2useCount.replace(key, oldCount + 1);
        useCount2keys.get(oldCount).remove(key);
        if (useCount2keys.get(oldCount).isEmpty()) {
            useCount2keys.remove(oldCount);
        }

        useCount2keys.putIfAbsent(oldCount + 1, new LinkedHashSet<>());
        useCount2keys.get(oldCount + 1).add(key);

        return map.get(key);
    }

    /**
     * Add key-value pair to cache
     *
     * @param key   key, not null
     * @param value value
     * @implNote if cache size is exceeded, then least frequently element will be deleted
     */
    @Override
    public void set(K key, V value) {
        if (map.containsKey(key)) {
            map.replace(key, value);
            get(key); // increment usage of key
            return;
        }

        if (map.size() == capacity) {
            Integer minUseCount = useCount2keys.firstKey();
            LinkedHashSet<K> leastUsageKeys = useCount2keys.get(minUseCount);
            K leastUsageKey = leastUsageKeys.iterator().next();
            leastUsageKeys.remove(leastUsageKey);
            key2useCount.remove(leastUsageKey);
            map.remove(leastUsageKey);

            if (leastUsageKeys.isEmpty()) {
                useCount2keys.remove(minUseCount);
            }
        }

        map.put(key, value);
        key2useCount.put(key, 1);

        useCount2keys.putIfAbsent(1, new LinkedHashSet<>());
        useCount2keys.get(1).add(key);
    }
}
