package com.javarush.task.task36.task3605;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        TreeSet<Character> set = new TreeSet<Character>();

        while (reader.ready()) {

            String line = reader.readLine();
            char[] chars = line.toLowerCase().toCharArray();
            Character[] characters = new Character[chars.length];
            for (int i = 0; i < characters.length; i++) {
                characters[i] = chars[i];
            }

            for (Character character : characters) {
                if (Character.isAlphabetic(character)) {
                    set.add(character);
                }
            }
        }
        //System.out.println(set);

        Iterator<Character> iterator = set.iterator();
        for (int i = 0; iterator.hasNext() && i<5; i++) {
            System.out.print(iterator.next());
        }
    }
}
