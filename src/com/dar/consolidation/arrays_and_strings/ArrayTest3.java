package com.dar.consolidation.arrays_and_strings;

import java.util.Arrays;

/**
 * @author :wx
 * @description :
 * @create :2022-10-30 21:47:00
 */
public class ArrayTest3 {
    public static void main(String[] args) {
        int[] a = new int[10];
        for (int i = 0; i < 10 ; i++) {
            a[i] = i;
        }
        for (int i = 0; i < a.length; i++) {//0 1 2 3 4 5 6 7 8 9
            System.out.print(a[i] + " ");
        }
        System.out.println();
        for (int i : a) {//0 1 2 3 4 5 6 7 8 9
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println(Arrays.toString(a));//[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
    }
}
