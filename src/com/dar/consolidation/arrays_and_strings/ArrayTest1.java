package com.dar.consolidation.arrays_and_strings;

/**
 * @author :wx
 * @description :
 * @create :2022-10-30 20:38:00
 */
public class ArrayTest1 {
    public static void main(String[] args) {
        int[] a = {1,2,3,4,5};
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);//12345
        }
        System.out.println();
        int[] b;
//        System.out.println(b);
        b = a;
        b[0] = 6;
        for (int i = 0; i < b.length; i++) {
            System.out.print(b[i]);//62345
        }
        System.out.println();
        System.out.println(a[0]);//6
    }
}
