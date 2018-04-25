package com.example.javaadvance.thread.base;

/**
 * 竞态条件
 * 所谓竞态条件(race condition)是指，当多个线程访问和操作同一个对象时，
 * 最终执行结果与执行时序有关，可能正确也可能不正确
 */
public class CounterThread extends Thread {

//    private static int counter ;
//    private static AtomicInteger counter = new AtomicInteger() ;
    private volatile static int counter ;

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            counter += 1;
//            counter.incrementAndGet();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int num = 100;

        Thread[] threads = new Thread[num];

        for (int i = 0; i < num; i++) {
            threads[i] = new CounterThread();
            threads[i].start();
        }

        for (int i = 0; i < num; i++) {
            threads[i].join();
        }

        System.out.println(counter);
    }
}
