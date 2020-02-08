package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {

    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) {

        Date date1 = new Date();
        for (String s : strings) {
            ids.add(shortener.getId(s));
        }
        Date date2 = new Date();

        return date2.getTime() - date1.getTime();
    }

    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) {

        Date date1 = new Date();
        for (Long l : ids) {
            strings.add(shortener.getString(l));
        }
        Date date2 = new Date();

        return date2.getTime() - date1.getTime();

    }

    @Test
    public void testHashMapStorage () {
        HashMapStorageStrategy hmss = new HashMapStorageStrategy();
        HashBiMapStorageStrategy hbmss = new HashBiMapStorageStrategy();

        Shortener shortener1 = new Shortener(hmss);
        Shortener shortener2 = new Shortener(hbmss);

        Set<String> origStrings = new HashSet<>();
        for(int i=0; i < 10_000; i++) {
            origStrings.add(Helper.generateRandomString());
        }

        Set<Long> ids = new HashSet<>();
        long time1 = getTimeToGetIds(shortener1,origStrings, ids);
        long time2 = getTimeToGetIds(shortener2,origStrings,ids);

        Assert.assertNotEquals(time1,time2);

        long time3 = getTimeToGetStrings(shortener1,ids, origStrings);
        long time4 = getTimeToGetStrings(shortener2,ids,origStrings);

        Assert.assertEquals(time3,time4,30);

    }


}
