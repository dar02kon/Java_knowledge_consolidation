package com.dar.consolidation.java_collections;

/**
 * @author :wx
 * @description :
 * @create :2022-11-13 14:56:00
 */
public class GenericsTest2 {
    public static void main(String[] args) {
        Container<Integer> container1 = new Container<>();
        container1.setT(15);
        System.out.println(container1.getT());//15
        Container<Double> container2 = new Container<>();
        System.out.println(container2.getT());//null
    }
}
class Container<T>{
    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}