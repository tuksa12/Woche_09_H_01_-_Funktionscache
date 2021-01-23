package pgdp.function;

import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionCache {
    //Attributes
    private final static int DEFAULT_CACHE_SIZE = 10_000;
    //Empty Constructor
    private FunctionCache() {
    }

    public static <T, R> Function<T, R> cached(Function<T, R> function, int maximalCacheSize){
        Cache cache = new Cache(maximalCacheSize);
        Function<T, R> result = t -> {
            if(!cache.containsKey(t)){
                cache.put(t,function.apply(t));
            }
            return (R) cache.get(t);
        };
      return result;
    }
    public static <T, R> Function<T, R> cached(Function<T, R> function){
        Cache cache = new Cache(DEFAULT_CACHE_SIZE);
        Function<T, R> result = t -> {
            if(!cache.containsKey(t)){
                cache.put(t,function.apply(t));
            }
            return (R) cache.get(t);
        };
        return result;
    }

    public static <T, U, R> BiFunction<T, U, R> cached(BiFunction<T, U, R> biFunction, int maximalCacheSize){
        Cache cache = new Cache(maximalCacheSize);
        BiFunction<T, U, R> result = (t, u) -> {
            Pair<T, U> pair = new Pair<>(t,u);
            if(!cache.containsKey(pair.hashCode())){
                cache.put(pair.hashCode(),biFunction.apply(t,u));
            }
            return (R) cache.get(pair.hashCode());
        };
        return result;
    }
    public static <T, U, R> BiFunction<T, U, R> cached(BiFunction<T, U, R> biFunction){
        Cache cache = new Cache(DEFAULT_CACHE_SIZE);
        BiFunction<T, U, R> result = (t, u) -> {
            Pair<T, U> pair = new Pair<>(t,u);
            if(!cache.containsKey(pair.hashCode())){
                cache.put(pair.hashCode(),biFunction.apply(t,u));
            }
            return (R) cache.get(pair.hashCode());
        };
        return result;
    }

    public static <T, R> Function<T, R> cachedRecursive(BiFunction<T, Function<T, R>, R> function, int maximalCacheSize){
        Cache cache = new Cache(maximalCacheSize);
//        Function<T, R> result = new Function<T, R>() {
//            @Override
//            public R apply(T t) {
//                if (!cache.containsKey(t)){
//                    cache.put(t,function.apply(t,cachedRecursive()))
//                }
//            }
//        }
        BiFunction<T,Function<T, R>, R> result = new BiFunction<T, Function<T, R>, R>() {
            @Override
            public R apply(T t, Function<T, R> trFunction) {
                if(!cache.containsKey(t)){
                    cache.put(t,trFunction.apply(t));
                }
                return (R) cache.get(t);
            }
        };
        return cachedRecursive(result);
        }

    public static <T, R> Function<T, R> cachedRecursive(BiFunction<T, Function<T, R>, R> function){
        Cache cache = new Cache(DEFAULT_CACHE_SIZE);
        BiFunction<T,Function<T, R>, R> result = new BiFunction<T, Function<T, R>, R>() {
            @Override
            public R apply(T t, Function<T, R> trFunction) {
                if(!cache.containsKey(t)){
                    cache.put(t,trFunction.apply(t));
                }
                return (R) cache.get(t);
            }
        };
        return cachedRecursive(result);

    }

    private static class Pair<T, U> {
        T t;
        U u;

        public Pair(T t, U u) {
            this.t = t;
            this.u = u;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair<?, ?> pair = (Pair<?, ?>) o;
            return Objects.equals(t, pair.t) && Objects.equals(u, pair.u);
        }

        @Override
        public int hashCode() {
            return Objects.hash(t, u);
        }

    }
}
