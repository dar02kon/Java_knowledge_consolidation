package com.dar.consolidation.object_oriented_programming.user_interface;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 21:02:00
 */
public class Cat implements Animal{
    @Override
    public void eat() {
        System.out.println("猫在吃");
    }

    @Override
    public void play() {
        System.out.println("猫在玩");
    }
}
