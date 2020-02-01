package com.javarush.task.task35.task3507;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

/*
ClassLoader - что это такое?
*/
public class Solution {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Set<? extends Animal> allAnimals = getAllAnimals(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + Solution.class.getPackage().getName().replaceAll("[.]", "/") + "/data");
        System.out.println(allAnimals);
    }

    public static Set<? extends Animal> getAllAnimals(String pathToAnimals) throws IllegalAccessException, InstantiationException {
        //System.out.println(pathToAnimals);
        Set<Animal> set = new HashSet<>();

        File[] files = new File(pathToAnimals).listFiles();
        for (File f : files) {
            MyClassLoader loader = new MyClassLoader(f.getAbsolutePath());
            Class<?> loadedClass = loader.loadClass();

            boolean ready  = false;

            Class<?>[] interfaces = loadedClass.getInterfaces();
            for (Class<?> i : interfaces) {
                if (i.getSimpleName().toLowerCase().equals("animal")) {
                    ready = true;
                    break;
                }else{
                    ready = false;
                }
            }

            if (ready) {
                try {
                    Constructor<?> constructor = loadedClass.getConstructor();
                    Animal o = (Animal) loadedClass.newInstance();
                    set.add(o);
                } catch (NoSuchMethodException ignored) {}
            }
        }
        return set;
    }

    public static class MyClassLoader extends ClassLoader{
        private String CLASSPATH;
        FileInputStream fis;
        byte[] bytes;
        Class<?> clazz;

        public MyClassLoader (String classPath) {
            this.CLASSPATH = classPath;
        }

        public Class<?> loadClass() {
            try {
                fis = new FileInputStream(CLASSPATH);
                bytes = new byte[fis.available()];
                fis.read(bytes,0,fis.available());
            } catch (IOException e) {
                e.printStackTrace();
            }
            File classFile = new File(CLASSPATH);

            clazz = super.defineClass(null,bytes,0, (int) classFile.length());
            return clazz;
        }
    }

}
