package com.example.thread.hello;

import java.util.Date;

/**
 * 中断(interrupt)表示一个线程应该停止当前所做的事而去另外一件事。通常中断是一个线程给另外一个线程发送中断信号，程序员自行决定如如何进行响应，也就是说收到中断信号后，接下来该做什么。通常情况下，线程收到中断信号后，采取的操作都是停止运行。
 * <p>
 * 我们可以在一个线程对象A中调用另一个线程对象B的interrupt方法，此时线程B就会收到中断信号。
 */
public class InterruptDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread t=new Thread(){
            @Override
            public void run() {
                //预期循环10次
                for (int i = 0; i <10 ; i++) {
                    try {
                        Thread.sleep(4000);
                        System.out.println("自定义线程:当前时间："+new Date().toLocaleString());
                    } catch (InterruptedException e) {//这个异常由sleep方法抛出
                        e.printStackTrace();
                        System.out.println("自定义线程:收到中断信号，总共循环了"+i+"次...");
                        //接受到中断信号时，停止运行
//                        return;
                    }
                }
            }
        };
        t.start();
        //主线程休眠9秒
        Thread.sleep(9000);
        System.out.println("主线程：等待9秒后发送中断信号...");
        t.interrupt();
    }

}
