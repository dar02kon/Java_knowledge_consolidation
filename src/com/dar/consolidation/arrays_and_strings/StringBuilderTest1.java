package com.dar.consolidation.arrays_and_strings;

/**
 * @author :wx
 * @description :
 * @create :2022-11-01 10:53:00
 */
public class StringBuilderTest1 {
    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        System.out.println(stringBuilder.length());//0
        stringBuilder.append("dar").append("02");
        System.out.println(stringBuilder);//dar02
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("123");
        System.out.println(stringBuffer);
    }
}
