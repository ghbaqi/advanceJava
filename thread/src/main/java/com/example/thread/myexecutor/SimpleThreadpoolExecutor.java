package com.example.thread.myexecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 自定义线程池
 * 线程池 + 任务队列 + 线程工厂
 */
public class SimpleThreadpoolExecutor {

    private BlockingQueue<Runnable> taskQueue = null;//任务队列
    private List<WorkerThread>      threads   = new ArrayList<>();//线程池
    private boolean                 isStopped = false;

    public SimpleThreadpoolExecutor(int noOfThreads, int maxNoOfTasks) {
        taskQueue = new LinkedBlockingQueue<>();
        for (int i = 0; i < noOfThreads; i++) {
            threads.add(new WorkerThread(taskQueue));
        }
        for (WorkerThread thread : threads) {
            thread.start();
        }
    }

    public synchronized void execute(Runnable task) {
        if (this.isStopped)
            throw
                    new IllegalStateException("SimpleThreadPoolExecutor is stopped");

        this.taskQueue.add(task);
    }

    public synchronized void stop() {
        this.isStopped = true;
        for (WorkerThread thread : threads) {
            thread.stop();//循环中断每一个线程
        }
    }

}

class WorkerThread extends Thread {

    private BlockingQueue<Runnable> taskQueue = null;
    private boolean                 isStopped = false;

    public WorkerThread(BlockingQueue<Runnable> queue) {
        taskQueue = queue;
    }

    public void run() {
        //因为需要不断从的任务列出中取出task执行，因此需要放在一个循环中，否则线程对象执行完一个任务就会立刻结束
        while (!isStopped()) {
            try {
                Runnable runnable = taskQueue.take();
                runnable.run();
            } catch (Exception e) {
                // 写日志或者报告异常,
                // 但保持线程池运行.
            }
        }
    }

    public synchronized void toStop() {
        isStopped = true;
        this.interrupt(); //如果线程正在任务队列中获取任务，或者没有任务被阻塞，需要响应这个中断
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }

}
