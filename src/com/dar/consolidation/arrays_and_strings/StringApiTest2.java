package com.dar.consolidation.arrays_and_strings;

/**
 * @author :wx
 * @description :
 * @create :2022-11-01 16:35:00
 */
public class StringApiTest2 {
    public static void main(String[] args) {
        String s = "1234";
        String s2 = new String("1234");
        System.out.println(s.compareTo("12345"));//-1
        System.out.println(s.compareTo(s2));//0
    }
}
