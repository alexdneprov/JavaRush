package com.javarush.task.task33.task3310;

public class ExceptionHandler {

    public static void log (Exception e) {
        System.out.println(e.toString() + " in " + e.getLocalizedMessage());
        e.printStackTrace();
    }
}
