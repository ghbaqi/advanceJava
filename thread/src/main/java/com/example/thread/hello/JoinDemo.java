package com.example.thread.hello;

/**
 * Thread类有一个join方法，其作用是：
 * 在A线程中调用了另外一个线程对象B的join方法时，那么A线程必须等待B线程执行完才能继续往下执行。
 */

/**
 * join方法的实际作用
 * <p>
 * 考虑某个网页上需要展示给用户看的数据存在两个不同的数据库里面。假设从A库查询需要5秒，
 * 从B库查询需要2秒。如果我们在一个线程中运行，那么总共就需要7秒。但是如果我们使用了以上的方式，
 * 使用2个线程分别去查，先执行完的等待后执行完的，也就是说只需要2秒就查询完成的那个任务等待需要5秒的那个任务，
 * 那么总共我们只需要5秒就可以查询到所有的数据，将其返回给用户。
 */
public class JoinDemo {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println("程序开始运行...");
        Thread t = new Thread() {
            @Override
            public void run() {
                try {//模拟自定义线程干某个事花了5秒
                    System.out.println("自定义线程执行开始...");
                    Thread.sleep(5000);
                    System.out.println("自定义线程执行完毕...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();

        //模拟主线干其他事花了2秒
        Thread.sleep(2000);
        System.out.println("主线程执行完毕，等待t线程执行完成...");
        //主线程2秒后就可以继续执行，但是其必须执行的条件是t线程必须执行完成，此时调用t的join方法
        long joinstart = System.currentTimeMillis();
        t.join();
        System.out.println("主线程：t执行已经执行完毕...，等待了" + (System.currentTimeMillis() - joinstart) / 1000 + "秒");
        System.out.println("程序运行总时间..." + (System.currentTimeMillis() - start) / 1000 + "秒");
    }

}
