package com.example.thread.aqs;

import java.util.Date;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        Mutex mutex = new MutexImpl();
        for (int i = 0; i < 5; i++) {
            new MutexThread("线程" + i, mutex).start();
        }
    }

    static class MutexThread extends Thread {
        private Mutex mutex;

        public MutexThread(String name, Mutex mutex) {
            this.mutex = mutex;
            this.setName(name);
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + "启动..");
            mutex.lock();
            System.out.println(Thread.currentThread().getName() + "获取锁成功..");
            try {
                System.out.println(Thread.currentThread().getName() + "开始执行,当前时间:" + new Date().toLocaleString());
                sleep(1000);//假设线程执行需要1秒钟
                System.out.println(Thread.currentThread().getName() + "结束执行,当前时间:" + new Date().toLocaleString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println(Thread.currentThread().getName() + "释放锁..");
                mutex.release();
            }

        }
    }
}
