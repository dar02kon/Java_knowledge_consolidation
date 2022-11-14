package com.dar.consolidation.java_collections;

/**
 * @author :wx
 * @description :
 * @create :2022-11-13 14:56:00
 */
public class GenericsTest1 {
    // 泛型方法 printArray
    public static <E> void printArray(E[] inputArray) {
        // 输出数组元素
        for (E element : inputArray) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    public static void main(String args[]) {
        // 创建不同类型数组： Integer, Double 和 Character
        Integer[] intArray = {1, 2, 3, 4, 5};
//        Double[] doubleArray = {1.1, 2.2, 3.3, 4.4};
//        Character[] charArray = {'H', 'E', 'L', 'L', 'O'};

        System.out.println("整型数组元素为:");
        printArray(intArray); // 传递一个整型数组
//
//        System.out.println("双精度型数组元素为:");
//        printArray(doubleArray); // 传递一个双精度型数组
//
//        System.out.println("字符型数组元素为:");
//        printArray(charArray); // 传递一个字符型数组
    }
}
