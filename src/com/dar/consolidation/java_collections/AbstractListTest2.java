package com.dar.consolidation.java_collections;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-11-16 17:22:00
 */
public class AbstractListTest2 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        List<Integer> subList = list.subList(0, 5);
        System.out.println(list);//[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
        subList.clear();
        System.out.println(list);//[5, 6, 7, 8, 9]
    }
}
