package com.javarush.task.task35.task3509;

import java.util.*;


/* 
Collections & Generics
*/
public class Solution {

    public static void main(String[] args) {
    }

    public static <T> ArrayList<T> newArrayList(T... elements) {
        ArrayList<T> list = new ArrayList<>();
        for(Object o : elements) {
            list.add((T) o);
        }
        return (ArrayList) list;
    }

    public static <T> HashSet<T> newHashSet(T... elements) {
        Set<T> set = new HashSet<>();
        Collections.addAll(set, elements);
        return (HashSet) set;
    }

    public static <K,V> HashMap<K,V> newHashMap(List<? extends K> keys, List<? extends V> values) {
        if (keys.size() != values.size()){
            throw new IllegalArgumentException("Size does not match");
        }
        Map<K,V> map = new HashMap<>();
        for(int i=0; i<keys.size(); i++) {
            map.put(keys.get(i),values.get(i));
        }
        return (HashMap) map;
    }
}
