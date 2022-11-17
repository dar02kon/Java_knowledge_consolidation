package com.dar.consolidation.java_collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-11-17 21:09:00
 */
public class HashSetTest1 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        HashSet<Integer> hashSet = new HashSet<>(list);
        System.out.println(hashSet);
        hashSet.clone();
    }
}
