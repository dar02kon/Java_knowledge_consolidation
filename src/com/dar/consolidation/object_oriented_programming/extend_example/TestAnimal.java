package com.dar.consolidation.object_oriented_programming.extend_example;

/**
 * @author :wx
 * @description :
 * @create :2022-10-26 15:45:00
 */
public class TestAnimal {
    public static void main(String[] args) {
        Animal animal1 = new Cat("猫",1);
        Animal animal2 = new Dog("狗",3);
        animal1.eat();
        animal2.eat();
    }
}
