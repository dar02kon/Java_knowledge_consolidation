package com.dar.consolidation.basic_data_type;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * @author :wx
 * @description :
 * @create :2022-10-23 17:13:00
 */
public class HighPrecisionTest {
    public static void main(String[] args) {
        BigDecimal bigDecimal1 = new BigDecimal("1.1");
        BigDecimal bigDecimal2 = new BigDecimal("1.10");
        System.out.println(bigDecimal1.equals(bigDecimal2));//false
        System.out.println(bigDecimal1.compareTo(bigDecimal2));//0

        System.out.println(bigDecimal1.add(bigDecimal2));//2.20  1.1+1.10
        System.out.println(bigDecimal1.multiply(bigDecimal2));//1.210  1.1*1.10
        System.out.println(bigDecimal1.divide(new BigDecimal("3"),3, BigDecimal.ROUND_DOWN));//保留三位小数，直接省略多余的小数
    }
}
