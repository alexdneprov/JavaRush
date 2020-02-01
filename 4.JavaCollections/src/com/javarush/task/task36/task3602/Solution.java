package com.javarush.task.task36.task3602;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* 
Найти класс по описанию
*/
public class Solution {
    public static void main(String[] args)  {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        List<Class<?>> list = new ArrayList<>();

        Class<?>[] classes = Collections.class.getDeclaredClasses();
        for (Class<?> c : classes) {

            Class<?>[] interfaces = c.getInterfaces();
            for (Class<?> i : interfaces) {
                if (i.getSimpleName().matches("List")) {
                    list.add(c);
                }
            }

            Class<?>[] superClassInterfaces = c.getSuperclass().getInterfaces();
            for (Class<?> sci : superClassInterfaces) {
                if (sci.getSimpleName().matches("List")) {
                    list.add(c);
                }
            }
        }

        list.removeIf(lc -> !Modifier.isPrivate(lc.getModifiers()));
        list.removeIf(lc -> !Modifier.isStatic(lc.getModifiers()));

        //System.out.println(list.size());

        for (Class<?> c : list) {

            try {

                Method m = c.getDeclaredMethod("get",int.class);
                m.setAccessible(true);

                Constructor<?> constructor = c.getDeclaredConstructor();
                constructor.setAccessible(true);

                Object o = null;

                o = constructor.newInstance();
                m.invoke(o,5);

            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                Throwable t = e.getCause();
                if (t instanceof IndexOutOfBoundsException) {
                    return c;
                }
            }
        }

    return null;
    }
}
