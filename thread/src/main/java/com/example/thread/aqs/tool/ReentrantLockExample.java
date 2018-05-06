package com.example.thread.aqs.tool;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    private static ReentrantLock lock      = new ReentrantLock();
    private static Condition     condition = lock.newCondition();

    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("thread 01 do");
                    condition.await();
                    System.out.println("thread 01 continue");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    lock.lock();
                    System.out.println("thread 02 do");
                    Thread.sleep(2000);
                    condition.signalAll();
                    System.out.println("thread 02 continue");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            }
        }).start();
    }
}
