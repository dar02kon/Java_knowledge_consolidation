package com.dar.consolidation.java_collections;

import java.util.HashMap;
import java.util.Map;

/**
 * @author :wx
 * @description :
 * @create :2022-11-20 20:59:00
 */
public class MapTest1 {
    public static void main(String[] args) {
        Map<Integer,Integer> map = new HashMap<>();
        map.put(null,null);
        map.forEach((k,v)->{
            System.out.println(k+" "+v);//null null
        });
        map.put(null,666);
        map.put(1,555);
        map.put(0,999);
        map.forEach((k,v)->{
            //null 666 //覆盖了前面的值
            //0 999
            //1 555
            System.out.println(k+" "+v);
        });
    }
}
