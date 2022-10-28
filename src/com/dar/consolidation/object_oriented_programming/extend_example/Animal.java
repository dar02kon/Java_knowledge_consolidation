package com.dar.consolidation.object_oriented_programming.extend_example;

/**
 * @author :wx
 * @description :
 * @create :2022-10-26 15:35:00
 */
public class Animal {
    protected String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public void eat(){
        System.out.println("animal eat");
    }
}
