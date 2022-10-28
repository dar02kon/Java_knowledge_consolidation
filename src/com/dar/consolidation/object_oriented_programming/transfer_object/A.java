package com.dar.consolidation.object_oriented_programming.transfer_object;

import java.io.Serializable;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 19:21:00
 */
public class A implements Cloneable, Serializable {
    private String name;
    private int age;
    @Override
    protected A clone() throws CloneNotSupportedException {
        return (A)super.clone();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public A(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
   public void show(){
       System.out.println("A{" +
               "name='" + name + '\'' +
               ", age=" + age +
               '}');
   }
}
