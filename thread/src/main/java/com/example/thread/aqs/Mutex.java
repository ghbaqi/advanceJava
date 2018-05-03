package com.example.thread.aqs;

public interface Mutex {

    //获取锁
    void lock();

    //释放锁
    void release();
}
