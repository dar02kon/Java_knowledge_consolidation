package com.dar.consolidation.object_oriented_programming.transfer_object;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 19:20:00
 */
public class TransferObjectTest1 {
    /**
     * fun()函数调用前：A{name='test1', age=12}
     * fun()函数调用前com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
     * fun()函数中：A{name='test2', age=21}
     * fun()函数中：com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
     * fun()函数调用后：A{name='test2', age=21}
     * fun()函数调用后com.dar.consolidation.object_oriented_programming.transfer_object.A@1b6d3586
     */
    public static void main(String[] args) {
        A a = new A("test1",12);
        System.out.print("fun()函数调用前：");
        a.show();
        System.out.println("fun()函数调用前"+a);
        fun(a);
        System.out.print("fun()函数调用后：");
        a.show();
        System.out.println("fun()函数调用后"+a);
    }

    public static void fun(A a){
        a.setName("test2");
        a.setAge(21);
        System.out.print("fun()函数中：");
        a.show();
        System.out.println("fun()函数中："+a);
    }
}
