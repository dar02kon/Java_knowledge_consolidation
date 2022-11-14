package com.dar.consolidation.java_collections;

import java.util.function.Consumer;

/**
 * @author :wx
 * @description :
 * @create :2022-11-14 21:34:00
 */
public class ConsumerTest1 {
    public static void main(String[] args) {
        Student student = new Student("麻衣", 99);

        Consumer<Student> consumer = new Consumer<Student>() {
            @Override
            public void accept(Student student) {
                student.setName("02");
                student.setScore(100);
            }
        };
        /**        使用lambda简写
         *         Consumer<Student> consumer = student1 -> {
         *             student1.setName("02");
         *             student1.setScore(100);
         *         };
         */
        consumer.accept(student);

        System.out.println("姓名：" + student.getName() + " ，分数：" + student.getScore());//姓名：02 ，分数：100
    }
}

class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
