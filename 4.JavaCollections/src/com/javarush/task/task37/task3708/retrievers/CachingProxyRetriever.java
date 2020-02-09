package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.storage.Storage;

public class CachingProxyRetriever implements Retriever {

    private OriginalRetriever retriever;
    private LRUCache<Long,Object> lruCache;

    public CachingProxyRetriever (Storage storage) {
        this.retriever = new OriginalRetriever(storage);
        this.lruCache = new LRUCache<>(5);
    }

    @Override
    public Object retrieve(long id) {
        Object o = lruCache.find(id);
        if (o == null) {
            Object oFromStorage = retriever.retrieve(id);
            lruCache.set(id,oFromStorage);
            return oFromStorage;
        }
        return o;
    }
}
