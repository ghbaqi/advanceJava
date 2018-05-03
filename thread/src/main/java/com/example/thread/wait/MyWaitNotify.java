package com.example.thread.wait;


/**
 * 一个线程一旦调用了任意对象的wait()方法，就会变为非运行状态，直到另一个线程调用了同一个对象的notify()方法。
 * 为了调用 wait()或者notify()，线程必须先获得那个对象的锁。也就是说，线程必须在同步块里调用wait()或者notify()。
 */

/**
 * 如你所见，不管是等待线程还是唤醒线程都在同步块里调用wait()和notify()。
 * 这是强制性的！一个线程如果没有持有对象锁，将不能调用 wait()，notify()或者notifyAll()。
 * 否则，会抛出IllegalMonitorStateException异常。
 */
public class MyWaitNotify {

    Object waitObject = new Object();

    public void doWait() {
        synchronized (waitObject) {
            try {
                waitObject.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void doNotify() {
        synchronized (waitObject) {
            waitObject.notify();
        }
    }
}

/**
 * sleep()方法与wait()方法区别。二者的作用都是进行等待。区别在于
 * 1、sleep()方法是Thread对象中定义的方法，而wait()方法定义在Object类中
 * 2、可以在任意地方调用线程对象的sleep方法，但是wait()方法必须位于同步代码块或者同步方法中
 * 3、线程在sleep的时候，并不会释放锁，因此其他线程无法获取到锁，因此也无法执行。而wait方法在执行的时候会释放锁，因此其他线程可以获取到锁，可以有机会运行。
 */
