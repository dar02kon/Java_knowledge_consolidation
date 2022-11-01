package com.dar.consolidation.arrays_and_strings;

import java.util.Arrays;

/**
 * @author :wx
 * @description :
 * @create :2022-10-31 10:30:00
 */
public class ArraysEqualsTest {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5,6,7};
        int[] b = {1,2,3,4};
        int[] c = {1,2,3,4,5,6,7};
        System.out.println(Arrays.equals(a, b));//false
        System.out.println(Arrays.equals(a, c));//true
    }
}
