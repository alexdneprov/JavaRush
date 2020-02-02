package com.javarush.task.task36.task3606;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/* 
Осваиваем ClassLoader и Reflection
*/
public class Solution {
    private List<Class> hiddenClasses = new ArrayList<>();
    private String packageName;

    public Solution(String packageName) {
        this.packageName = packageName;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Solution solution = new Solution(Solution.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "com/javarush/task/task36/task3606/data/second");
        solution.scanFileSystem();
        System.out.println(solution.getHiddenClassObjectByKey("secondhiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("firsthiddenclassimpl"));
        System.out.println(solution.getHiddenClassObjectByKey("packa"));
    }

    public void scanFileSystem() throws ClassNotFoundException {
        File path = new File(packageName);
        File[] files = path.listFiles();
        if (files != null) {
            for (File f : files) {
                MyClassLoader loader = new MyClassLoader(f.getAbsolutePath());
                Class<?> clazz = loader.loadClass();
                hiddenClasses.add(clazz);
            }
        }
    }

    public HiddenClass getHiddenClassObjectByKey(String key) {
        Object o = null;
        for (Class<?> clazz : hiddenClasses) {
            if (clazz.getSimpleName().toLowerCase().startsWith(key.toLowerCase())) {
                try {
                    Constructor<?>[] constructors = clazz.getDeclaredConstructors();
                    for (Constructor<?> co : constructors) {
                        if (co.getParameterCount() == 0) {
                            co.setAccessible(true);
                            o = co.newInstance();
                            break;
                        }
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return (HiddenClass) o;
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

            clazz = super.defineClass(null,bytes,0, (int) classFile.length());      //name = null, because it was unknown before loading.
            return clazz;
        }
    }

}

