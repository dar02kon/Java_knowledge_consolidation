package com.dar.consolidation.java_collections;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @author :wx
 * @description :
 * @create :2022-12-03 13:52:00
 */
public class HashMapTest1 {

    public static void main(String[] args) {
        HashMap<Integer,Integer> hashMap1 = new HashMap<>();
        LinkedHashMap<Integer,Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put(1,2);
        linkedHashMap.put(3,4);
        System.out.println(linkedHashMap);
    }
}
