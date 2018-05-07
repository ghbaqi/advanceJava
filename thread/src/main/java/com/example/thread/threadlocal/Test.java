package com.example.thread.threadlocal;

/**
 * 同一个 ThreadLocal 所包含的对象（对ThreadLocal< String >而言即为 String 类型变量），在不同的 Thread 中有不同的副本
 * 因为每个 Thread 内有自己的实例副本，且该副本只能由当前 Thread 使用。这是也是 ThreadLocal 命名的由来
 */

/**
 * 总的来说，ThreadLocal 适用于每个线程需要自己独立的实例且该实例需要在多个方法中被使用，也即变量在线程间隔离而在方法或类间共享的场景。
 */

/**
 * 由 thread 来维护 map    ！！！！！
 * 此 map 的 key 为 threadLocal 对象的弱引用 ， value 为该线程绑定的实例
 */
public class Test {
}
