package com.dar.consolidation.object_oriented_programming.transfer_object;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 19:49:00
 */
public class CloneTest {
    public static void main(String[] args) {
        A a = new A("test",18);
        try {
            A clone = a.clone();
            System.out.println(a);//com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
            System.out.println(clone);//com.dar.consolidation.object_oriented_programming.transfer_object.A@4554617c
            clone.setName("clone");
            a.show();//A{name='test', age=18}
            clone.show();//A{name='clone', age=18}
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
