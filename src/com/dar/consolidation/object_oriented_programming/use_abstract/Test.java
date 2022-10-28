package com.dar.consolidation.object_oriented_programming.use_abstract;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 20:51:00
 */
public class Test {
    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.eat();//猫吃鱼
        Dog dog = new Dog();
        dog.eat();//狗啃骨头
        Animal animal = new Animal() {
            @Override
            void eat() {
                System.out.println("123");
            }
        };
        animal.eat();
    }
}
