package com.dar.consolidation.arrays_and_strings;

import java.util.Arrays;

/**
 * @author :wx
 * @description :
 * @create :2022-10-31 10:13:00
 */
public class ArrayTest5 {
    public static void main(String[] args) {
        int[] a = {7,6,5,4,3,2,1};
        System.out.println(Arrays.binarySearch(a, 9));//-8
        System.out.println(Arrays.binarySearch(a,3));//2
    }
}
