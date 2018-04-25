package com.example.javaadvance.thread.communicate;

import java.util.Date;
import java.util.concurrent.CyclicBarrier;

/**
 * 从使用场景上来说，CyclicBarrier是让多个线程互相等待某一事件的发生，
 * 然后同时被唤醒。而上文讲的CountDownLatch是让某一线程等待多个线程的状态，
 * 然后该线程被唤醒。
 */
public class CyclicBarrierDemo {

    // 从执行结果可以看到，每个线程都不会在其它所有线程执行await()方法前继续执行，而等所有线程都执行await()方法后所有线程的等待都被唤醒从而继续执行
    public static void main(String[] args) {
        int totalThread = 5;
        CyclicBarrier barrier = new CyclicBarrier(totalThread);

        for(int i = 0; i < totalThread; i++) {
            String threadName = "Thread " + i;
            new Thread(() -> {
                System.out.println(String.format("%s\t%s %s", new Date(), threadName, " is waiting"));
                try {
                    barrier.await();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println(String.format("%s\t%s %s", new Date(), threadName, "ended"));
            }).start();
        }
    }

}
