package com.dar.consolidation.object_oriented_programming.create_object;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 17:29:00
 */
public class CreateByClone {
    public static void main(String[] args) {
        A a = new A();//使用构造方法
        a.setS1("我是第一个A");
        try {
            A clone = a.clone();//我是A对象
            clone.setS1("我是第二个A");
            System.out.println(a);//com.dar.consolidation.object_oriented_programming.create_object.A@1b6d3586
            System.out.println(clone);//com.dar.consolidation.object_oriented_programming.create_object.A@4554617c
            System.out.println(a.getS1());//我是第一个A
            System.out.println(clone.getS1());//我是第二个A
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
