package pgdp.function;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cache<K, V> extends LinkedHashMap<K, V> {
    private final int maximalCacheSize;

    public int getMaximalCacheSize() {
        return maximalCacheSize;
    }

    public Cache(int initialCapacity) {
        super(initialCapacity);
        this.maximalCacheSize = initialCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.size() > maximalCacheSize;
    }
}
