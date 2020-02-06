package com.javarush.task.task33.task3310.strategy;

import java.util.Objects;

public class OurHashMapStorageStrategy implements StorageStrategy {

    static final int DEFAULT_INITIAL_CAPACITY = 16;

    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];

    int size;

    int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);

    float loadFactor = DEFAULT_LOAD_FACTOR;



    int hash(Long k) {
        return Objects.hash(k);
    }

    int indexFor(int hash, int length) {
        return hash & length - 1;
    }

    Entry getEntry(Long key) {
        int index = indexFor(hash(key),table.length);
        if (table[index] != null) {
            if (table[index].key.equals(key)) {
                return table[index];
            }else{
                Entry e = table[index].next;
                while (e != null) {
                    if (e.key.equals(key)){
                        return e;
                    }else e = e.next;
                }
            }
        }
        return null;
    }

    void resize(int newCapacity) {
        threshold = (int) (newCapacity * loadFactor);

        Entry[] old = table;
        table = new Entry[newCapacity];

        for (int i = 0; i < old.length; i++) {
            Entry e = old[i];
            if (e != null) {
                if (e.next == null) {
                    int newIndexForOldElement = indexFor(old[i].hash,table.length);
                    table[newIndexForOldElement] = e;
                }else {
                    Entry el = e.next;
                    while (el != null) {
                        int elemIndex = indexFor(el.hash,newCapacity);
                        if (table[elemIndex] == null) {
                            table[elemIndex] = el;
                        }else {
                            Entry el2 = table[elemIndex].next;
                            while (el2 != null) {
                                if (el2.next == null) {
                                    el2.next = el;
                                    break;
                                }
                                el2 = el2.next;
                            }
                        }
                        el = el.next;
                    }
                }
            }
        }
    }

    void transfer(Entry[] newTable) {
        table = newTable;

    }

    void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex];
        if (e == null) {
            createEntry(hash,key,value,bucketIndex);
        }else{
            Entry el = e.next;
            while (el != null) {
                if (el.next == null) {
                    el.next = new Entry(hash,key,value,null);
                }
                el = el.next;
            }
        }
    }
    void createEntry(int hash, Long key, String value, int bucketIndex) {
        table[bucketIndex] = new Entry(hash,key,value,null);
    }


    @Override
    public boolean containsKey(Long key) {
        for (Entry entry : table) {
            if (entry.key.equals(key)) {
                return true;
            }else {
                Entry e = entry.next;
                while (e != null) {
                    if (e.next.key.equals(key)) {
                        return true;
                    }
                    e = e.next;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(String value) {
        for (Entry entry : table) {
            if (entry != null) {
                if (entry.value.equals(value)) {
                    return true;
                }else {
                    Entry e = entry.next;
                    while (e != null) {
                        if (e.next.value.equals(value)) {
                            return true;
                        }
                        e = e.next;
                    }
                }
            }

        }
        return false;
    }

    @Override
    public void put(Long key, String value) {
        if (size > threshold) {
            resize(table.length * 2);
        }
        addEntry(hash(key),key,value,indexFor(hash(key),table.length));
        size++;
     }

    @Override
    public Long getKey(String value) {
        for (Entry entry : table) {
            if (entry != null) {
                if (entry.value.equals(value)) {
                    return entry.key;
                }else {
                    Entry e = entry.next;
                    while (e != null) {
                        if (e.value.equals(value)) {
                            return e.key;
                        }
                        e = e.next;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        Entry entry = getEntry(key);
        if (entry != null) {
            return entry.value;
        }
        return null;
    }
}
