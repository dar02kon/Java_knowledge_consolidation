package com.dar.consolidation.arrays_and_strings;

/**
 * @author :wx
 * @description :
 * @create :2022-11-01 17:15:00
 */
public class StringBuilderApiTest1 {
    public static void main(String[] args) {
        StringBuilder s = new StringBuilder("123");
        s.insert(0,"456");
        System.out.println(s);//456123
    }
}
