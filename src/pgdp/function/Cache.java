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

//    public V get(Object key){
//        V value;
//        if(super.containsKey(key)){
//            value = super.get(key);
//            return value;
//        }else{
//            return null;
//        }
//    }

    public void insert(K key, V value){
        super.put(key, value);
        if(super.containsKey(key)){
            super.remove(key);
        }
        super.put(key,value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.size() > maximalCacheSize;
    }
}
