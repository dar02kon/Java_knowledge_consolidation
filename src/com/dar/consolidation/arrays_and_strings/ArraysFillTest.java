package com.dar.consolidation.arrays_and_strings;

import java.util.Arrays;

/**
 * @author :wx
 * @description :
 * @create :2022-10-31 10:40:00
 */
public class ArraysFillTest {
    public static void main(String[] args) {
        int[] a = new int[10];
        Arrays.fill(a,6);
        System.out.println(Arrays.toString(a));//[6, 6, 6, 6, 6, 6, 6, 6, 6, 6]
    }
}
