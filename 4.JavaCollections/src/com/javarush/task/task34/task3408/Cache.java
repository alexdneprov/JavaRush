package com.javarush.task.task34.task3408;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

public class Cache<K, V> {
    private Map<K, V> cache = new WeakHashMap<>();


    public V getByKey(K key, Class<V> clazz) throws Exception {
        V val = cache.get(key);
        if (val == null) {
            Class[] params = new Class[] {key.getClass()};
            Constructor<V> newVal = clazz.getConstructor(params);
            V newObj = newVal.newInstance(key);
            cache.put(key, newObj);
        }
        return cache.get(key);
    }

    public boolean put(V obj) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        try{
            Method method = obj.getClass().getDeclaredMethod("getKey");
            method.setAccessible(true);
            K key = (K) method.invoke(obj);
            cache.put(key,obj);
            return true;
        }catch (Exception e){};

        return false;
    }

    public int size() {
        return cache.size();
    }
}
