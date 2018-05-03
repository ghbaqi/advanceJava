package com.example.thread.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 5、编写同步组件需要注意的地方
 * <p>
 * 1)使用新的接口和实现包装同步组件：在我们编写一个同步组件的时候，例如我们想实现一个独占锁，
 * 假设为Sync，其继承了AQS。只需要在Sync类中覆写tryRelease和tryAcquire即可，但是由于继承AQS的时候，
 * 会把tryAcquireShared、tryReleaseShared等共享锁方法也继承下来。而Sync并不会实现这些共享式同步组件的方法，
 * 因为Sync只是一个独占锁而已，从业务含义上，因此应该将这些方法屏蔽，从而防止用户误操作。
 * 按照最佳实现，屏蔽的方式是定义一个新的接口，假设用Mutex表示，这个接口只定义了独占锁相关方法，
 * 再编写一个类MutexImpl实现Mutex接口，而对于同步组件Sync类的操作，都封装在MutexImpl中。
 * <p>
 * 2)同步组件推荐定义为静态内部类：因为某个同步组件通常是为实现特定的目的而实现，可能只适用于特定的场合。
 * 如果某个同步组件不具备通用性，我们应该将其定义为一个私有的静态内部类。结合第一点，
 * 我们编写的同步组件Sync应该是MutexImpl的一个私有的静态内部类。
 */
public class MutexImpl implements Mutex {

    private Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);

    }

    @Override
    public void release() {
        sync.release(1);
    }

    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            return compareAndSetState(0,1);
        }

        @Override
        protected boolean tryRelease(int arg) {
            return compareAndSetState(1,0);
        }
    }

}
