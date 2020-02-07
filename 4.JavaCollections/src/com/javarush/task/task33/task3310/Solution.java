package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.javarush.task.task33.task3310.Helper.generateRandomString;

public class Solution {

    public static void main(String[] args) {
        //testStrategy(new HashMapStorageStrategy(),10000);
        //System.out.println("--------------------------------");
        //testStrategy(new OurHashMapStorageStrategy(), 10000);
        System.out.println("--------------------------------");
        testStrategy(new FileStorageStrategy(),100);
        //System.out.println("--------------------------------");
        //testStrategy(new OurHashBiMapStorageStrategy(),17);
    }

    public static Set<Long> getIds (Shortener shortener, Set<String> strings) {
        Set<Long> idsSet = new HashSet<>();



        for (String s : strings) {
            idsSet.add(shortener.getId(s));
        }

        return idsSet;
    }

    public static Set<String> getStrings (Shortener shortener, Set<Long> keys) {
        Set<String> strings = new HashSet<>();

        for (Long key : keys) {
            System.out.println("key = " + key + "; " + shortener.getString(key));
        }

        for (Long key : keys) {
            strings.add(shortener.getString(key));
        }

        return strings;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {
        try{
            Helper.printMessage(strategy.getClass().getSimpleName());

            Set<String> elements = new HashSet<>();
            for (int i = 0; i < elementsNumber; i ++) {
                elements.add(generateRandomString());
            }

            Shortener shortener = new Shortener(strategy);

            Date date1 = new Date();
            Set<Long> idsSet = getIds(shortener, elements);
            Date date2 = new Date();
            Long dateResult1 = date2.getTime() - date1.getTime();
            Helper.printMessage(dateResult1.toString() + " ms заняло добавление ключей");
            Helper.printMessage(idsSet.size() + " - добавлено ключей");      // размер сета ключей

            Date date3 = new Date();
            Set<String> stringsSet = getStrings(shortener, idsSet);
            Date date4 = new Date();
            Long dateResult2 = date4.getTime() - date3.getTime();
            Helper.printMessage(dateResult2.toString() + " ms заняло добавление строк");
            Helper.printMessage(stringsSet.size() + " - добавлено строк");      // размер сета строк



            Helper.printMessage(elements.size() == stringsSet.size() ? "Тест пройден." : "Тест не пройден.");
        }catch (Exception e) {
            Helper.printMessage("Тест не пройден.");
            ExceptionHandler.log(e);
        }
    }
}
