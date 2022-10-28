package com.dar.consolidation.object_oriented_programming.create_object;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 17:05:00
 */
public class CreateByNew {
    public static void main(String[] args) {
        A a = new A();
        a.show();
    }
}
class A implements Cloneable{
    private String s1;
    public String getS1() {
        return s1;
    }
    public void setS1(String s1) {
        this.s1 = s1;
    }
    @Override
    protected A clone() throws CloneNotSupportedException {
        return (A)super.clone();
    }
    public A(){
        System.out.println("使用构造方法");
    }
    public void show(){
        System.out.println("我是A对象");
    }
}
