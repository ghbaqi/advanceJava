package com.example.thread.callable;

import java.util.Date;

public class SpinLock {

    public volatile static String sharedVariable;//共享变量
    public static void main(String[] args) {
        //启动一个线程执行运行
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);//进行运算操作，以休眠代替
                    sharedVariable="Hello";
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }.start();

        System.out.println("开始时间："+new Date());

        //自旋锁，就是不断进行循环，起到阻塞的作用
        while(sharedVariable==null){}
        System.out.println(sharedVariable);

        System.out.println("结束时间:"+new Date());
    };
}
