package com.dar.consolidation.object_oriented_programming.user_interface;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 21:03:00
 */
public class Dog implements Animal{
    @Override
    public void eat() {
        System.out.println("狗在吃");
    }

    @Override
    public void play() {
        System.out.println("狗在玩");
    }
}
