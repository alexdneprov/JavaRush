package com.javarush.task.task35.task3512;

public class Generator<T> {
    private Class<T> eventClass;

    public Generator (Class<T> clazz) {
        this.eventClass = clazz;
    }

    T newInstance() throws IllegalAccessException, InstantiationException {
        return eventClass.newInstance();
    }
}
