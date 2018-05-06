package com.example.thread.aqs.tool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountdownLatchExample {

    private static int count = 10;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            int num = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        doWork(num);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        latch.countDown();
                    }
                }
            });
        }

        System.out.println("1111");
        latch.await();
        System.out.println("finish...");


    }

    private static void doWork(int num) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("dowork num = " + num);
    }
}
