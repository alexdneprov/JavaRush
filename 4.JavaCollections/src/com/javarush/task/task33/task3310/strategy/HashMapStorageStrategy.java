package com.javarush.task.task33.task3310.strategy;

import com.javarush.task.task33.task3310.ExceptionHandler;
import com.javarush.task.task33.task3310.Helper;

import java.util.HashMap;
import java.util.Map;

public class HashMapStorageStrategy implements StorageStrategy {

    private HashMap<Long,String> data = new HashMap<>();

    @Override
    public boolean containsKey(Long key) {
        try{
            return data.containsKey(key);
        }catch (Exception e) {
            Helper.printMessage("Тест не пройден.");
            ExceptionHandler.log(e);
        }
        return false;
    }

    @Override
    public boolean containsValue(String value) {
        try{
            return data.containsValue(value);
        }catch (Exception e) {
            Helper.printMessage("Тест не пройден.");
            ExceptionHandler.log(e);
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {
        try{
            data.put(key,value);
        }catch (Exception e) {
            Helper.printMessage("Тест не пройден.");
            ExceptionHandler.log(e);
        }
    }

    @Override
    public Long getKey(String value) {
        try{
            for (Map.Entry<Long,String> pair : data.entrySet()) {
                if (pair.getValue().equals(value)){
                    return pair.getKey();
                }
            }
        }catch (Exception e) {
            Helper.printMessage("Тест не пройден.");
            ExceptionHandler.log(e);
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        try{
            return data.get(key);
        }catch (Exception e) {
            Helper.printMessage("Тест не пройден.");
            ExceptionHandler.log(e);
        }
        return null;
    }
}
