package com.dar.consolidation.arrays_and_strings;

/**
 * @author :wx
 * @description :
 * @create :2022-11-01 14:58:00
 */
public class StringTest5 {
    public static void main(String[] args) {
        String s = "123";
//        System.out.println(System.identityHashCode(s));//460141958
        String intern = s.intern();
//        System.out.println(System.identityHashCode(intern));//460141958
        String s1 = "123";
//        System.out.println(System.identityHashCode(s1));//460141958

        String s2 = new String("456");
//        System.out.println(System.identityHashCode(s2));//1163157884
        String s3 = "456";
//        System.out.println(System.identityHashCode(s3));//1956725890
        String intern2 = s2.intern();
//        System.out.println(System.identityHashCode(intern2));//1956725890
        String s4 = new String("456");
//        System.out.println(System.identityHashCode(s4));//356573597
    }
}
