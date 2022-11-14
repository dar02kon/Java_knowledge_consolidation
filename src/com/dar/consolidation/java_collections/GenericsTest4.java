package com.dar.consolidation.java_collections;

/**
 * @author :wx
 * @description :
 * @create :2022-11-13 19:28:00
 */
public class GenericsTest4 {
    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
    public static void main(String[] args) {
        GenericsTest4 simpleHolder = new GenericsTest4();
        simpleHolder.setObject("Item");
        String s = (String) simpleHolder.getObject();
    }
}

