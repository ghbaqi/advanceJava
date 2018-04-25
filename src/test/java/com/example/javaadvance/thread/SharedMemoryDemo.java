package com.example.javaadvance.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * 在代码中，定义了一个静态变量shared和静态内部类ChildThread，
 * 在main方法中，创建并启动了两个ChildThread对象，传递了相同的list对象，
 * ChildThread的run方法访问了共享的变量shared和list，
 * main方法最后输出了共享的shared和list的值，大部分情况下，会输出期望的值：
 * <p>
 * 2
 * [Thread-0, Thread-1]
 * <p>
 * <p>
 */

/**
 * 通过这个例子，我们想强调说明执行流、内存和程序代码之间的关系。

 该例中有三条执行流，一条执行main方法，另外两条执行ChildThread的run方法。
 不同执行流可以访问和操作相同的变量，如本例中的shared和list变量。
 不同执行流可以执行相同的程序代码，如本例中incrShared方法，ChildThread的run方法，被两条ChildThread执行流执行，incrShared方法是在外部定义的，但被ChildThread的执行流执行，在分析代码执行过程时，理解代码在被哪个线程执行是很重要的。
 当多条执行流执行相同的程序代码时，每条执行流都有单独的栈，方法中的参数和局部变量都有自己的一份。

 当多条执行流可以操作相同的变量时，可能会出现一些意料之外的结果

 */

public class SharedMemoryDemo {

    private static int shared = 0;

    private static void increse() {
        shared++;
    }

    static class ChildThread extends Thread {
        private List<String> list;

        public ChildThread(List<String> list) {
            this.list = list;
        }

        @Override
        public void run() {
            increse();
            list.add(Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> list = new ArrayList<>();

        ChildThread thread01 = new ChildThread(list);
        ChildThread thread02 = new ChildThread(list);

        thread01.start();
        thread02.start();

        thread01.join();
        thread02.join();

        System.out.println(shared);
        System.out.println(list);
    }

}
