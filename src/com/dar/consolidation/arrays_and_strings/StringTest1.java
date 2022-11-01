package com.dar.consolidation.arrays_and_strings;

/**
 * @author :wx
 * @description :
 * @create :2022-10-31 20:22:00
 */
public class StringTest1 {
    public static void main(String[] args) {
        String s = "123";
        String s1 = s;
        s = s+"";
        System.out.println(System.identityHashCode(s));//460141958
        System.out.println(System.identityHashCode(s1));//1163157884
        System.out.println(s1==s);//false
    }
}
