package com.dar.consolidation.java_collections;

import java.util.Date;

/**
 * @author :wx
 * @description :
 * @create :2022-11-13 19:28:00
 */
public class GenericsTest5<T> {
    private T object;

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public static void main(String[] args) {
        GenericsTest5<? extends Fruit> simpleHolder = new GenericsTest5<>();
        GenericsTest5<Apple> apple = new GenericsTest5<>();
        simpleHolder = apple;
        Apple object = (Apple)simpleHolder.getObject();
    }
}
class Food{

}
class Fruit extends Food{

}
class Apple extends Fruit{

}


