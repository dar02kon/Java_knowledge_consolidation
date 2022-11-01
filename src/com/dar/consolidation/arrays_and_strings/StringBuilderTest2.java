package com.dar.consolidation.arrays_and_strings;

/**
 * @author :wx
 * @description :
 * @create :2022-11-01 14:47:00
 */
public class StringBuilderTest2 {
    public static void main(String[] args) {
        String s = "0";
        for (int i = 1; i < 10; i++) {
            s += i;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("666");
        System.out.println(stringBuilder);
    }
}
