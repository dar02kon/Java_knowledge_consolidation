package com.dar.consolidation.java_collections;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-11-13 19:28:00
 */
public class GenericsTest7{
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }
        for (int i : list) {
            System.out.print(i+" ");//0 1 2 3 4
        }
    }
}

