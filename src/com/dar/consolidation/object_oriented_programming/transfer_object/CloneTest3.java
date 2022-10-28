package com.dar.consolidation.object_oriented_programming.transfer_object;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 20:23:00
 */
public class CloneTest3 {
    public static void main(String[] args) {
        A a = new A("test",15);
        C c = new C("男",a);
        System.out.println(a);//com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
        C c1 = c.myClone();
        System.out.println(c1);//com.dar.consolidation.object_oriented_programming.transfer_object.C@15aeb7ab
    }
}
