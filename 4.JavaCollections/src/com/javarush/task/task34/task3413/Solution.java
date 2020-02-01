package com.javarush.task.task34.task3413;

/* 
Кеш на основании SoftReference
*/

public class Solution {
    public static void main(String[] args) {
        SoftCache cache = new SoftCache();

        for (long i = 0; i < 10; i++) {
            cache.put(i, new AnyObject(i, null, null));
            System.out.println(cache.get(i));
        }

        System.out.println("\n"+"\n"+"\n");

        for (long i = 0; i < 20; i++) {

            System.out.println(cache.put(i, new AnyObject(i, null, null)));
        }
    }
}