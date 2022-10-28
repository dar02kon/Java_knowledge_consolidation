package com.dar.consolidation.object_oriented_programming.extend_example;

/**
 * @author :wx
 * @description :
 * @create :2022-10-26 15:44:00
 */
public class Dog extends Animal{
    public Dog(String name, int age) {
        super(name, age);
    }
    @Override
    public void eat() {
        System.out.println(age + "岁的"+ name+"在啃骨头捏");
    }
}
