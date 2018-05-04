package com.example.thread.cyclibarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

// 当四个线程都到达barrier状态后，会从四个线程中选择最后一个执行完的线程去执行Runnable

/**
 * CyclicBarrier实际用途：假设我们要导入一批数据，因为数据量较大有4亿条，所以我们希望启动四个线程，
 * 每个线程负责导入1亿条数据，我们希望所有的数据导入完成之后，还在数据库中插入一条记录，表示任务执行完成，
 * 此时就可以使用CyclicBarrier。
 */
public class Test02 {

    public static void main(String[] args) {
        int N = 4;
        CyclicBarrier barrier = new CyclicBarrier(N, new Runnable() {
            @Override
            public void run() {
                System.out.println("当前线程" + Thread.currentThread().getName());
            }
        });

        for (int i = 0; i < N; i++)
            new Writer(barrier).start();
    }

    static class Writer extends Thread {
        private CyclicBarrier cyclicBarrier;

        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
            try {
                Thread.sleep(5000);      //以睡眠来模拟写入数据操作
                System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("所有线程写入完毕，继续处理其他任务...");
        }
    }
}
