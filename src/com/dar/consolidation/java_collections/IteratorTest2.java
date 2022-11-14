package com.dar.consolidation.java_collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Consumer;

/**
 * @author :wx
 * @description :
 * @create :2022-11-14 19:45:00
 */
public class IteratorTest2 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        ListIterator<Integer> iterator = list.listIterator(4);
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
    }
}

