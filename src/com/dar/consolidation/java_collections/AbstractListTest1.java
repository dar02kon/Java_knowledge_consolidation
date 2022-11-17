package com.dar.consolidation.java_collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-11-16 16:12:00
 */
public class AbstractListTest1 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()){
            iterator.next();
            iterator.remove();
        }
        System.out.println(list);
    }
}
