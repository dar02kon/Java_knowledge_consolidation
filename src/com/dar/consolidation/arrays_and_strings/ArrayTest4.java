package com.dar.consolidation.arrays_and_strings;

import java.util.Arrays;

/**
 * @author :wx
 * @description :
 * @create :2022-10-31 09:38:00
 */
public class ArrayTest4 {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6};
        int[] copy = Arrays.copyOf(a, 2);
        a[0] = 0;
        System.out.println(Arrays.toString(copy));//[1, 2]
    }
}
