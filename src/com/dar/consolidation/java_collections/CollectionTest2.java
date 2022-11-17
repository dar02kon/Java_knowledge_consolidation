package com.dar.consolidation.java_collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-11-15 20:54:00
 */
public class CollectionTest2 {
    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            arrayList.add(i);
        }
        arrayList.remove(0);
        System.out.println(arrayList);
        List<Integer> list = arrayList;
        list.remove(0);
        System.out.println(list);
        Collection<Integer> collection = list;
        collection.remove(0);
        System.out.println(collection);
    }
}
