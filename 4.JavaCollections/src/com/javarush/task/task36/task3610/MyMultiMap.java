package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        int count = 0;
        for (Map.Entry<K,List<V>> pair : map.entrySet()) {
            for (V v : pair.getValue()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public V put(K key, V value) {
        if (map.containsKey(key)) {
            List<V> list = map.get(key);
            if (list.size() < repeatCount) {
                list.add(value);
                int ind = list.indexOf(value);
                return list.get(ind -1);
            }else if (list.size() == repeatCount) {
                list.add(value);
                list.remove(0);
                int ind = list.indexOf(value);
                return list.get(ind -1);
            }
        }else{
            List<V> list = new ArrayList<V>();
            list.add(value);
            map.put(key,list);
            return null;
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        if (map.containsKey(key)) {
            List<V> list = map.get(key);
            if (list.size() > 0) {
                V deletedElement = list.remove(0);
                if (list.size() == 0 ){
                    map.remove(key);
                }
                return deletedElement;
            }
        }
        return null;
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        List<V> vList = new ArrayList<>();
        for (Map.Entry<K,List<V>> pair : map.entrySet()) {
            vList.addAll(pair.getValue());
        }
        return vList;
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        boolean b = false;
        for (Map.Entry<K, List<V>> pair : map.entrySet()){
            if (b = pair.getValue().contains(value)) {
                break;
            }
        }
        return b;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        System.out.println(sb.toString());
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}