package com.javarush.task.task37.task3707;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {

    private static final Object PRESENT = new Object();
    private transient HashMap<E,Object> map;

    public AmigoSet () {
        this.map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        int capacity = Math.max(16, (int)(collection.size()/.75f)+1);

        this.map = new HashMap<>(capacity);
        addAll(collection);
    }

    @Override
    public boolean add(E e) {
        return map.put(e,PRESENT) == null;
    }

    @Override
    public boolean addAll(Collection c) {
        try {
            super.addAll(c);
            return true;
        }catch (Exception exc) {}
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public void clear() {
        map.clear();
    }
    @Override
    public boolean remove(Object o) {
        return map.remove(o) == null;
    }

    @Override
    public Object clone() {
        try {
            AmigoSet copy = (AmigoSet)super.clone();
            copy.map = (HashMap) map.clone();
            return copy;
        } catch (Exception e) {
            throw new InternalError(e);
        }
    }


    private void writeObject (ObjectOutputStream outputStream) {
        try {
            outputStream.defaultWriteObject();

            HashMapReflectionHelper helper = new HashMapReflectionHelper();

            int capacity = helper.callHiddenMethod(map,"capacity");
            float loadFactor = helper.callHiddenMethod(map,"loadFactor");
            int size = map.size();

            outputStream.writeInt(capacity);
            outputStream.writeFloat(loadFactor);
            outputStream.writeInt(size);

            for (E key : map.keySet()) {
                outputStream.writeObject(key);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readObject (ObjectInputStream inputStream) {
        try {
            inputStream.defaultReadObject();

            int capacity  = inputStream.readInt();
            float loadFactor  = inputStream.readFloat();
            int size = inputStream.readInt();

            this.map = new HashMap<>(capacity,loadFactor);

            for (int i=0; i<size; i++) {
                map.put((E) inputStream.readObject(),PRESENT);
            }


        }catch (ClassNotFoundException | IOException ignored) {}
    }

}
