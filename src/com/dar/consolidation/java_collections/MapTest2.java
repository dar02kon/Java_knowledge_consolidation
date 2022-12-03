package com.dar.consolidation.java_collections;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author :wx
 * @description :
 * @create :2022-11-22 19:17:00
 */
public class MapTest2 {
    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        for (int i = 5; i > 0; i--) {
            map.put(i+"",i);
        }
        map.put("a",0);
        map.put("b",0);
        map.put("c",0);
        List<Map.Entry<String, Integer>> collect = map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(
                Collectors.toList()
        );
        System.out.println(map);//{1=1, a=0, 2=2, b=0, 3=3, c=0, 4=4, 5=5}
        System.out.println(collect);//[1=1, 2=2, 3=3, 4=4, 5=5, a=0, b=0, c=0]
    }
}
