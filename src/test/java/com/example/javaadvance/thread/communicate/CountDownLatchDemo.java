package com.example.javaadvance.thread.communicate;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
// http://www.jasongj.com/java/thread_communication/
public class CountDownLatchDemo {

    /**
     * CountDownLatch适用场景
     * Java多线程编程中经常会碰到这样一种场景——某个线程需要等待一个或多个线程操作结束（或达到某种状态）才开始执行。
     * 比如开发一个并发测试工具时，主线程需要等到所有测试线程均执行完成再开始统计总共耗费的时间，
     * 此时可以通过CountDownLatch轻松实现。
     */
    public static void main(String[] args) throws InterruptedException {
        int totalThread = 3;
        long start = System.currentTimeMillis();
        CountDownLatch countDown = new CountDownLatch(totalThread);
        for (int i = 0; i < totalThread; i++) {
            final String threadName = "Thread " + i;
            new Thread(() -> {
                System.out.println(String.format("%s\t%s %s", new Date(), threadName, "started"));
                try {
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                countDown.countDown();
                System.out.println(String.format("%s\t%s %s", new Date(), threadName, "ended"));
            }).start();
            ;
        }
        countDown.await();
        long stop = System.currentTimeMillis();
        System.out.println(String.format("Total time : %sms", (stop - start)));
    }

}
