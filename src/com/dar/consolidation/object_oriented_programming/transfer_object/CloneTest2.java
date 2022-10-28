package com.dar.consolidation.object_oriented_programming.transfer_object;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 20:01:00
 */
public class CloneTest2 {
    public static void main(String[] args) {
        A a = new A("test",12);
        B b = new B("男",a);
        try {
            B clone = b.clone();
            System.out.println(b.getA());//com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
            System.out.println(clone.getA());//com.dar.consolidation.object_oriented_programming.transfer_object.A@4554617c
            System.out.println(b.getA().getName());//test
            clone.getA().setName("clone");
            System.out.println(b.getA().getName());//test
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
