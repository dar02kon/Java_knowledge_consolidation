package com.dar.consolidation.object_oriented_programming.transfer_object;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 20:00:00
 */
public class B implements Cloneable{
    private String sex;
    private A a;

    @Override
    protected B clone() throws CloneNotSupportedException {
        B clone = (B) super.clone();
        clone.setA(clone.getA().clone());
        return clone;
    }
    public B(String sex, A a) {
        this.sex = sex;
        this.a = a;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public A getA() {
        return a;
    }
    public void setA(A a) {
        this.a = a;
    }
}
