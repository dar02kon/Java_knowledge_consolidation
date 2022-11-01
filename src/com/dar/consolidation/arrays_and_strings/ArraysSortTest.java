package com.dar.consolidation.arrays_and_strings;

import java.util.Arrays;

/**
 * @author :wx
 * @description :
 * @create :2022-10-31 10:46:00
 */
public class ArraysSortTest {
    public static void main(String[] args) {
        int[] a = {7,6,5,4,3,2,1};
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));//[1, 2, 3, 4, 5, 6, 7]
    }
}
