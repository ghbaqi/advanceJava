package com.example.javaadvance.thread.base;

/**
 * 内存可见性
 * 多个线程可以共享访问和操作相同的变量，但一个线程对一个共享变量的修改，
 * 另一个线程不一定马上就能看到，甚至永远也看不到
 */

/**
 * 在计算机系统中，除了内存，数据还会被缓存在CPU的寄存器以及各级缓存中，
 * 当访问一个变量时，可能直接从寄存器或CPU缓存中获取，而不一定到内存中去取，
 * 当修改一个变量时，也可能是先写到缓存中，而稍后才会同步更新到内存中。
 * 在单线程的程序中，这一般不是个问题，但在多线程的程序中，尤其是在有多CPU的情况下，
 * 这就是个严重的问题。一个线程对内存的修改，另一个线程看不到，
 * 一是修改没有及时同步到内存，二是另一个线程根本就没从内存读。
 * <p>
 * 链接：https://juejin.im/post/58a1ba8786b599006b4877aa
 */
public class VisibilityDemo {

    //    private static  boolean shutDown = false;
    private static volatile boolean shutDown = false;


    static class HelloThread extends Thread {
        @Override
        public void run() {
            while (!shutDown) {
                //
            }
            System.out.println("exit hello");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new HelloThread().start();
        Thread.sleep(1000);
        shutDown = true;
        System.out.println("main exit");
    }

}
