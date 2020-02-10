package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
Древний Рим
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
    }

    public static int romanToInteger(String s) {

        char[] chars = s.toCharArray();
        int result = 0;
        int lastNumber = 0;

        for (int i = chars.length - 1; i >= 0; i--) {
            switch (chars[i]) {
                case ('I'):
                    result = calculateDec(1, lastNumber, result);
                    lastNumber = 1;
                    break;
                case ('V'):
                    result = calculateDec(5, lastNumber, result);
                    lastNumber = 5;
                    break;
                case ('X'):
                    result = calculateDec(10, lastNumber, result);
                    lastNumber = 10;
                    break;
                case ('L'):
                    result = calculateDec(50, lastNumber, result);
                    lastNumber = 50;
                    break;
                case ('C'):
                    result = calculateDec(100, lastNumber, result);
                    lastNumber = 100;
                    break;
                case ('D'):
                    result = calculateDec(500, lastNumber, result);
                    lastNumber = 500;
                    break;
                case ('M'):
                    result = calculateDec(1000, lastNumber, result);
                    lastNumber = 1000;
                    break;
            }
        }
        return result;
    }

    public static int calculateDec ( int dec, int lastNumber, int result  ){
        if (lastNumber > result) {
            return result - dec;
        } else {
            return result + dec;
        }
    }
}