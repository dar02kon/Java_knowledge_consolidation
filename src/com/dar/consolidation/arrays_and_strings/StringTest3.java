package com.dar.consolidation.arrays_and_strings;

/**
 * @author :wx
 * @description :
 * @create :2022-11-01 10:07:00
 */
public class StringTest3 {
    public static void main(String[] args) {
        final String h1 = "dar";
        final int num = 3;
        String h2 = "dar";
        String s1 = h1 + "02";
        String s2 = h2 + "02";
        String s3 = "12"+num;
        System.out.println((s1 == "dar02"));//true
        System.out.println((s2 == "dar02"));//false
        System.out.println(s3);//123
    }
}
