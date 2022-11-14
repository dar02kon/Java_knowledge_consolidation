package com.dar.consolidation.java_collections;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-11-13 19:28:00
 */
public class GenericsTest8<W, T> {
    void f(List<T> list) {

    }

    void f2(List<W> list) {

    }

}

interface Payable<T> {

}

class Employee implements Payable<Employee> {

}

class ComparablePet implements Comparable<ComparablePet> {
    @Override
    public int compareTo(ComparablePet o) {
        return 0;
    }
}

class FixedSizeStack<T> {
    private int index = 0;
    private Object[] storage;

    public FixedSizeStack(int size) {
        this.storage = new Object[size];
    }

    public void push(T t){
        storage[index++] = t;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        return (T) storage[--index];
    }
}
class Test{
    public static final int SIZE = 10;
    public static void main(String[] args) {
        FixedSizeStack<String> stack = new FixedSizeStack<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            stack.push(i+"");
        }
        for (int i = 0; i < SIZE; i++) {
            System.out.print(stack.pop() + " ");//9 8 7 6 5 4 3 2 1 0
        }
    }
}