package com.dar.consolidation.java_collections;

import java.util.*;

/**
 * @author :wx
 * @description :
 * @create :2022-11-14 19:45:00
 */
public class IteratorTest3 {
    public static void main(String[] args) {
        ReversibleArrayList<String> list = new ReversibleArrayList<>(Arrays.asList("1 2 3 4 5 6 7 8".split(" ")));
        for (String s : list) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (String s : list.reversed()) {
            System.out.print(s + " ");
        }
    }
}

class ReversibleArrayList<T> extends ArrayList<T> {
    public ReversibleArrayList(Collection<? extends T> c) {
        super(c);
    }

    public Iterable<T> reversed() {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    int current = size() - 1;

                    @Override
                    public boolean hasNext() {
                        return current > -1;
                    }

                    @Override
                    public T next() {
                        return get(current--);
                    }
                };
            }
        };
    }
}

