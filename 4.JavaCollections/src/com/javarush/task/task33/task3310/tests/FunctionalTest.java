package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;


public class FunctionalTest {

    public void testStorage (Shortener shortener) {
        String string_1 = "string_1";
        String string_2 = "string_2";
        String string_3 = "string_1";

        Long idForString_1 = shortener.getId(string_1);
        Long idForString_2 = shortener.getId(string_2);
        Long idForString_3 = shortener.getId(string_3);

        Assert.assertNotEquals(idForString_2,idForString_1);
        Assert.assertNotEquals(idForString_2,idForString_3);
        Assert.assertEquals(idForString_1,idForString_3);

        String expectedString_1 = shortener.getString(idForString_1);
        String expectedString_2 = shortener.getString(idForString_2);
        String expectedString_3 = shortener.getString(idForString_3);

        Assert.assertEquals(expectedString_1, string_1);
        Assert.assertEquals(expectedString_2, string_2);
        Assert.assertEquals(expectedString_3, string_3);
    }

    @Test
    public void testHashMapStorageStrategy () {
        HashMapStorageStrategy s = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(s);
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy() {
        OurHashMapStorageStrategy s = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(s);
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy () {
        FileStorageStrategy s = new FileStorageStrategy();
        Shortener shortener = new Shortener(s);
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy () {
        HashBiMapStorageStrategy s = new HashBiMapStorageStrategy();
        Shortener shortener = new Shortener(s);
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy () {
        DualHashBidiMapStorageStrategy s = new DualHashBidiMapStorageStrategy();
        Shortener shortener = new Shortener(s);
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy () {
        OurHashMapStorageStrategy s = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(s);
        testStorage(shortener);
    }
}