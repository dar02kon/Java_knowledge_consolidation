package com.dar.consolidation.object_oriented_programming.create_object;

import java.lang.reflect.Constructor;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 17:12:00
 */
public class CreateByReflect {
    //使用构造方法，创建了A对象
    //我是A对象
    public static void main(String[] args) {
        Class<A> aClass = A.class;
        try {
            A a = aClass.newInstance();
            a.show();
        } catch (Exception ignored) {
        }
        try {
            Constructor<A> constructor = A.class.getConstructor();
            A a = constructor.newInstance();
            a.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}