package com.dar.consolidation.basic_data_type;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author :wx
 * @description :
 * @create :2022-10-23 15:02:00
 */
public class MaxAndMinTest {
    public static void main(String[] args) {
        double d = 1.66666;
        /**
         * DecimalFormat转换
         */
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println(df.format(d));//1.67

        /**
         * String.format转换
         */
        System.out.println(String.format("%.2f", d));//1.67

        /**
         * BigDecimal的setScale方法
         */
        BigDecimal bg = new BigDecimal(d);
        double d1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//BigDecimal.ROUND_HALF_UP为四舍五入模式
        System.out.println(d1);//1.67

        /**
         * NumberFormat的setMaximumFractionDigits方法
         */
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        System.out.println(nf.format(d));//1.67
    }
}
/**
 * System.out.println(Integer.MIN_VALUE);//-2147483648
 * System.out.println(Integer.MAX_VALUE);//2147483647
 * System.out.println(Integer.MIN_VALUE-1);//2147483647
 * System.out.println(Integer.MAX_VALUE+1);//-2147483648
 */
/**
 *         System.out.println(Double.MIN_VALUE);//4.9E-324
 *         System.out.println(Double.MAX_VALUE);//1.7976931348623157E308
 *         System.out.println((long) Double.MIN_VALUE);//0
 *         System.out.println((long) Double.MAX_VALUE);//9223372036854775807
 */
/**
 *         double d = -1.111111111111111111111;
 *         System.out.println(d);//-1.1111111111111112
 *         System.out.println(1.4-1.1);//0.2999999999999998
 */

/**
 *         char c1='a';//定义一个char类型
 *         int i1 = c1;//char自动类型转换为int
 *         System.out.println(i1 + "  " + (char)i1);//97  a
 *         char c2 = 'A';//定义一个char类型
 *         int i2 = c2+1;//char 类型和 int 类型计算
 *         System.out.println(i2 + "  " + (char)i2);//66  B
 *
 *         double b = 1.999;
 *         System.out.println((int)b);//1  小数部位直接丢弃
 */