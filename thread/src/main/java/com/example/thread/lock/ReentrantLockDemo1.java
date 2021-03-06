package com.example.thread.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁是用来控制多个线程访问共享资源的方式，一般来说，一个锁能够防止多个线程同时访问共享资源
 * (但是有些锁可以允许多个线程并发的访问共享资源，比如读写锁)。在Lock接口出现以前，
 * Java程序是靠synchronized关键字实现锁功能的，而Java SE 5之后，并发包中新增了Lock接口（以及相关实现类）来实现锁的功能，
 * 它提供了与synchronized关键字类似的同步功能。只是在使用的时候，需要显示地获取和释放锁。
 * 虽然它缺少了(通过synchronized块或者方法所提供的)隐式获取释放锁的便捷性，
 * 但是确拥有了锁获取与释放的可操作性、可中断的获取锁以及超时获取锁等多种synchronized关键字所不具备的同步特性。
 * <p>
 * 使用synchronized关键字将会隐式的获取锁，但是它将锁的获取和释放固化了，也即是先获取，再释放。
 * 当然这种方式简化了锁的管理，可是扩展性没有显示的获取锁和释放来的好。例如，针对一个场景，手把手进行锁的获取和释放，
 * 先获得锁A，然后再获取锁B，当锁B获得后，释放锁A同时获取锁C，当锁C获得后，再释放B同时获取锁D，以此类推。这种场景下，
 * synchronized关键字就不是那么容易实现了，而使用Lock确容易许多。
 * Lock的使用也很简单。
 */
public class ReentrantLockDemo1 {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        new Thread("Thread A") {
            @Override
            public void run() {
                lock.lock();//加锁
                try {
                    work();//work
                } finally {
                    lock.unlock();//释放锁
                }

            }
        }.start();
        new Thread("Thread B") {
            @Override
            public void run() {
                lock.lock();//加锁
                try {
                    work();//work
                } finally {
                    lock.unlock();//释放锁
                }

            }
        }.start();
    }

    public static void work() {
        try {
            System.out.println(Thread.currentThread().getName() + " started to work,currrentTime:" + System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " wnd work,currrentTime:" + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
