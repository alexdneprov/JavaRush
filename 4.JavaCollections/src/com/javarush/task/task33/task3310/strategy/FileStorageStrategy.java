package com.javarush.task.task33.task3310.strategy;

import java.util.Objects;

public class FileStorageStrategy implements StorageStrategy {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final long DEFAULT_BUCKET_SIZE_LIMIT = 10_000;
    FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    long maxBucketSize;


    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }


    int hash(Long k) {
        return Objects.hash(k);
    }

    int indexFor(int hash, int length) {
        return hash & length - 1;
    }

    Entry getEntry(Long key) {
        int index = indexFor(hash(key),table.length);
        if (table[index] != null) {
            if (table[index].getEntry().key.equals(key)) {
                return table[index].getEntry();
            }else{
                Entry e = table[index].getEntry().next;
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

        FileBucket[] old = table;
        table = new FileBucket[newCapacity];

        for (FileBucket e : old) {
            if (e != null) {
                if (e.getEntry().next == null) {
                    int newIndexForOldElement = indexFor(e.getEntry().hash, table.length);
                    table[newIndexForOldElement] = e;
                } else {
                    Entry el = e.getEntry().next;
                    while (el != null) {
                        int elemIndex = indexFor(el.hash, newCapacity);
                        if (table[elemIndex].getEntry() == null) {
                            table[elemIndex].putEntry(el);
                        } else {
                            Entry el2 = table[elemIndex].getEntry().next;
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

    void addEntry(int hash, Long key, String value, int bucketIndex) {
        FileBucket e = table[bucketIndex];
        if (e == null) {
            createEntry(hash,key,value,bucketIndex);
        }else{
            Entry el = e.getEntry();
            while (el != null) {
                if (el.next == null) {
                    el.next = new Entry(hash,key,value,null);
                }
                el = el.next;
            }
        }
    }
    void createEntry(int hash, Long key, String value, int bucketIndex) {
        FileBucket fileBucket = new FileBucket();
        fileBucket.putEntry(new Entry(hash,key,value,null));
        table[bucketIndex] = fileBucket;
    }




    @Override
    public boolean containsKey(Long key) {
        for (FileBucket fileBucket : table) {
            Entry entry = fileBucket.getEntry();
            if (entry != null) {
                while (entry != null) {
                    if (entry.key.equals(key)) {
                        return true;
                    }else entry = entry.next;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(String value) {
        for (FileBucket fileBucket : table) {
            if (fileBucket != null) {
                Entry entry = fileBucket.getEntry();
                if (entry != null) {
                    while (entry != null) {
                        if (entry.value.equals(value)) {
                            return true;
                        }else entry = entry.next;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {
        for (FileBucket fileBucket : table) {
            if (fileBucket != null) {
                if (fileBucket.getFileSize() > getBucketSizeLimit()) {
                    resize(table.length * 2);
                    break;
                }
            }
        }
        addEntry(hash(key),key,value,indexFor(hash(key),table.length));
        size++;
    }

    @Override
    public Long getKey(String value) {
        for (FileBucket fileBucket : table) {
            if (fileBucket != null) {
                Entry entry = fileBucket.getEntry();
                while (entry != null) {
                    if (entry.value.equals(value)) {
                        return entry.key;
                    }else entry = entry.next;
                }
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        for (FileBucket fileBucket : table) {
            if (fileBucket != null) {
                Entry entry = fileBucket.getEntry();
                while (entry != null) {
                    if (entry.key.equals(key)) {
                        return entry.value;
                    }else entry = entry.next;
                }
            }
        }
        return null;
    }
}
