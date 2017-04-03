package com.example;

import java.util.Arrays;

class Student {
    String name;
    String sex;
    int age;

    Student(String name, String sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    void display() {
        System.out.println("your name is " + name);
        System.out.println("your sex is " + sex);
        System.out.println("your age is " + age);
    }
}
public class MyClass {
    public static void main (String[] args)
    {
        Student student =new Student("Erica","female",18);

        student.display();
    }
}