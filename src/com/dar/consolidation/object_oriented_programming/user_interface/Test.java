package com.dar.consolidation.object_oriented_programming.user_interface;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 21:04:00
 */
public class Test {
    public static void main(String[] args) {
        Cat cat = new Cat();
        cat.eat();//猫在吃
        cat.play();//猫在玩
        Dog dog = new Dog();
        dog.eat();//狗在吃
        dog.play();//狗在玩
        Animal animal = new Animal() {
            @Override
            public void eat() {
                System.out.println("吃");
            }

            @Override
            public void play() {
                System.out.println("玩");
            }
        };
        animal.eat();//吃
        animal.play();//玩
    }
}
