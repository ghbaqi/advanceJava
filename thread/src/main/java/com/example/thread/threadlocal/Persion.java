package com.example.thread.threadlocal;

public class Persion {

    private String name;
    private int    age;

    // 在多线程的情形下 ， 为每个线程绑定一份 StringBuilder 实例
    // 为每个线程创建一份变量实例
    private static ThreadLocal<StringBuilder> threadLocal = ThreadLocal.withInitial(() -> new StringBuilder());

    public Persion(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Persion() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void add(String s) {
        threadLocal.get().append(s);
    }

    public void print() {
        System.out.println("threadName = " + Thread.currentThread().getName() + " , threadLocal = "+threadLocal+", value = "+threadLocal.get().toString());
    }
}
