package com.dar.consolidation.java_collections;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author :wx
 * @description :
 * @create :2022-11-14 19:45:00
 */
public class IteratorTest1{
    public static void main(String[] args) {
        String[] nums = new String[5];
        for (int i = 0; i < 5 ; i++) {
            nums[i] = i+"";
        }
        MyIterator<String> iterator = new MyIterator<>(nums);
        iterator.forEachRemaining(v-> System.out.print(v+" "));//0 1 2 3 4
        System.out.println();
        while (iterator.hasNext()){
            String next = iterator.next();
            if(next.equals("2")){
                iterator.remove();//删除2后面一个元素3
            }
            System.out.print(next + " ");//0 1 2 4
        }
        System.out.println();
        iterator.forEachRemaining(v-> System.out.print(v+" "));//0 1 2 4

    }
}
class MyIterator<T> implements Iterator<T>{
    private int size;//数组大小
    private final T[] nums;//泛型数组，不能初始化，只能采用聚合的方式由外界传入
    private int count = 0;//遍历使用的下标
    MyIterator(T[] nums) {
        this.nums = nums;
        this.size = nums.length;
    }
    @Override
    public boolean hasNext() {
        return count<size;
    }
    @Override
    public T next() {
        return nums[count++];
    }

    @Override
    public void remove() {
        if(count>=size){
            Iterator.super.remove();
        } else {//删除当前下标所指元素
            for (int i = count; i < size-1; i++) {
                nums[count] = nums[count+1];
            }
            size = Math.max(size - 1, 0);//重新确定元素个数
        }
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        count=0;
        Iterator.super.forEachRemaining(action);
        count=0;
    }
}
