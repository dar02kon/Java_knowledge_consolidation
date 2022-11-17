package com.dar.consolidation.java_collections;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author :wx
 * @description :
 * @create :2022-11-16 21:18:00
 */
public class ArrayListTest1 {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 10; i > 0; i--) {
            list.add(i);
        }
        List<Integer> clone =(List<Integer>) list.clone();
        clone.add(5);
        System.out.println(clone);//[10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 5]
        System.out.println(list);//[10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
        clone.set(1,100);
        System.out.println(clone);//[10, 100, 8, 7, 6, 5, 4, 3, 2, 1, 5]
        System.out.println(list);//[10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
//        list.remove(new Integer(1));
//        System.out.println(list);//[10, 9, 8, 7, 6, 5, 4, 3, 2]
        list.remove(1);
//        System.out.println(list);//[10, 8, 7, 6, 5, 4, 3, 2]
//        list.sort(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {//升序，降序换一下位置
//                return o1-o2;
//            }
//        });

        ArrayList<Target> list1 = new ArrayList<>();
        list1.add(new Target("123"));
        list1.add(new Target("456"));
        list1.add(new Target("789"));
        ArrayList<Target> clone1 = (ArrayList<Target>)list1.clone();
        clone1.get(0).setName("000");
        System.out.println(clone1);//[Target{name='000'}, Target{name='456'}, Target{name='789'}]
        System.out.println(list1);//[Target{name='000'}, Target{name='456'}, Target{name='789'}]

    }
}
class Target{
    String name;

    public Target(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Target{" +
                "name='" + name + '\'' +
                '}';
    }
}

