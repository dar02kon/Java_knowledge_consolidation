package com.dar.consolidation.java_collections;

import java.util.LinkedList;

/**
 * @author :wx
 * @description :
 * @create :2022-11-17 15:44:00
 */
public class LinkedListTest1 {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            linkedList.add(i);
        }
        linkedList.add(5);
        System.out.println(linkedList);
        linkedList.removeLastOccurrence(5);
        System.out.println(linkedList);
        linkedList.descendingIterator();
    }
}
