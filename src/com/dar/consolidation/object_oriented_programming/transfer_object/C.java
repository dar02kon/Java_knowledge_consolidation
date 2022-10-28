package com.dar.consolidation.object_oriented_programming.transfer_object;

import java.io.*;

/**
 * @author :wx
 * @description :
 * @create :2022-10-28 20:20:00
 */
public class C implements Serializable {
    private String sex;
    private A a;
    public C(String sex, A a) {
        this.sex = sex;
        this.a = a;
    }
    public C myClone() {
        C c = null;
        try {
            // 写入字节流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            c = (C) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return c;
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
