package com.dar.consolidation.java_collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-11-13 14:56:00
 */
public class GenericsTest3 {
    public static void main(String[] args) {
        Class class1 = new ArrayList<String>().getClass();
        Class class2 = new ArrayList<Integer>().getClass();
        System.out.println(class1==class2);//true
        System.out.println(Arrays.toString(class1.getTypeParameters()));//[E]
        System.out.println(Arrays.toString(class2.getTypeParameters()));//[E]

    }
}
