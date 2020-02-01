package com.javarush.task.task36.task3604;

/* 
Разбираемся в красно-черном дереве
*/
public class Solution {
    public static void main(String[] args) {
        RedBlackTree rbt = new RedBlackTree();
        rbt.insert(43);
        rbt.insert(553);
        rbt.insert(23);


        System.out.println(rbt.isEmpty());
    }
}
