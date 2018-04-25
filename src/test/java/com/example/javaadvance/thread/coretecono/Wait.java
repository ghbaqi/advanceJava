package com.example.javaadvance.thread.coretecono;

import java.util.Date;

/**
 * wait方法是Object类的方法，这意味着所有的Java类都可以调用该方法。
 * sleep方法是Thread类的静态方法。
 * <p>
 * wait是在当前线程持有wait对象锁的情况下，暂时放弃锁，并让出CPU资源，
 * 并积极等待其它线程调用同一对象的notify或者notifyAll方法。
 * 注意，即使只有一个线程在等待，并且有其它线程调用了notify或者notifyAll方法，
 * 等待的线程只是被激活，但是它必须得再次获得锁才能继续往下执行。
 * 换言之，即使notify被调用，但只要锁没有被释放，
 * 原等待线程因为未获得锁仍然无法继续执行
 */
public class Wait {


    /**
     * 从运行结果可以看出
     * <p>
     * thread1执行wait后，暂停执行
     * thread2执行notify后，thread1并没有继续执行，因为此时thread2尚未释放锁，thread1因为得不到锁而不能继续执行
     * thread2执行完synchronized语句块后释放锁，thread1得到通知并获得锁，进而继续执行
     *
     */

    /**
     * 注意：wait方法需要释放锁，前提条件是它已经持有锁。
     * 所以wait和notify（或者notifyAll）方法都必须被包裹在synchronized语句块中，
     * 并且synchronized后锁的对象应该与调用wait方法的对象一样。
     * 否则抛出IllegalMonitorStateException
     */
    public static void main(String[] args) throws Exception {
        Thread thread01 = new Thread() {
            @Override
            public void run() {
                synchronized (Wait.class) {
                    System.out.println("thread01 run date = " + new Date());
                    try {
                        Wait.class.wait();
                        System.out.println("thread01 wait end date = " + new Date());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread01.start();

        Thread thread02 = new Thread() {
            @Override
            public void run() {
                synchronized (Wait.class) {
                    System.out.println("thread02 run date = " + new Date());
                    Wait.class.notify();
                    // Don't use sleep method to avoid confusing
                    for (long i = 0; i < 200000; i++) {
                        for (long j = 0; j < 100000; j++) {
                        }
                    }
                    System.out.println("thread02 release lock date = " + new Date());
                }

                for (long i = 0; i < 200000; i++) {
                    for (long j = 0; j < 100000; j++) {
                    }
                }
                System.out.println("thread02 end date = " + new Date());
            }
        };

        // Don't use sleep method to avoid confusing
        for (long i = 0; i < 200000; i++) {
            for (long j = 0; j < 100000; j++) {
            }
        }
        thread02.start();
    }

}
