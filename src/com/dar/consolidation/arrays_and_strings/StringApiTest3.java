package com.dar.consolidation.arrays_and_strings;

/**
 * @author :wx
 * @description :
 * @create :2022-11-01 17:05:00
 */
public class StringApiTest3 {
    public static void main(String[] args) {
        String s = "123456";
        String s1 = s.replaceAll("12", "00");
        System.out.println(s1);//003456
    }
}
