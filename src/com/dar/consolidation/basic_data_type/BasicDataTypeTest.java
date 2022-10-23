package com.dar.consolidation.basic_data_type;

/**
 * @author :wx
 * @description :
 * @create :2022-10-23 14:04:00
 */
public class BasicDataTypeTest {
    public static void main(String[] args) {
        // float
        /**
         * 基本类型：float 二进制位数：32
         * 包装类：java.lang.Float
         * 最小值：Float.MIN_VALUE=1.4E-45
         * 最大值：Float.MAX_VALUE=3.4028235E38
         */
        System.out.println("基本类型：float 二进制位数：" + Float.SIZE);
        System.out.println("包装类：java.lang.Float");
        System.out.println("最小值：Float.MIN_VALUE=" + Float.MIN_VALUE);
        System.out.println("最大值：Float.MAX_VALUE=" + Float.MAX_VALUE);
        System.out.println();

        // double
        /**
         * 基本类型：double 二进制位数：64
         * 包装类：java.lang.Double
         * 最小值：Double.MIN_VALUE=4.9E-324
         * 最大值：Double.MAX_VALUE=1.7976931348623157E308
         */
        System.out.println("基本类型：double 二进制位数：" + Double.SIZE);
        System.out.println("包装类：java.lang.Double");
        System.out.println("最小值：Double.MIN_VALUE=" + Double.MIN_VALUE);
        System.out.println("最大值：Double.MAX_VALUE=" + Double.MAX_VALUE);
        System.out.println();
    }
}
