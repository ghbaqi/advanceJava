package com.example.thread.threadlocal;

import java.util.concurrent.CountDownLatch;

// ThreadLocal 适用于变量在线程间隔离且在方法间共享的场景
public class ThreadLocalDemo {

    public static void main(String[] args) throws Exception {

        int threads = 3;
        CountDownLatch latch = new CountDownLatch(3);

        Persion persion = new Persion("ghbaqi", 20);
        for (int i = 0; i < threads; i++) {
            int num = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    persion.add(num + "");
                    persion.print();
                    latch.countDown();
                }
            }).start();
        }

        latch.await();
        System.out.println("finish...");

    }

}
