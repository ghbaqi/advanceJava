package com.example.javaadvance.threadlocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class ThreadLocalDemo {

    Logger logger = LoggerFactory.getLogger(ThreadLocalDemo.class);

    public static void main(String[] args) throws InterruptedException {

        int threads = 3;
        CountDownLatch countDownLatch = new CountDownLatch(threads);
        InnerClass innerClass = new InnerClass();
        for (int i = 0; i < threads; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 4; j++) {
                        innerClass.add(""+j);
                        innerClass.print();
                    }
                    innerClass.set("hello world");
                    countDownLatch.countDown();
                }
            };
            thread.setName("thread-"+i);
            thread.start();
        }

        countDownLatch.await();

        System.out.println("========================");


    }



    private static class InnerClass {

        Logger logger = LoggerFactory.getLogger(InnerClass.class);

        public void add(String str) {
            StringBuilder stringBuilder = Counter.counter.get();
            Counter.counter.set(stringBuilder.append(str));
        }

        public void print() {
            logger.info("threadName = {} , threadLocal hashcode = {}  , Instance hashcode = {} , value = {}"
            ,Thread.currentThread().getName()
            ,Counter.counter.hashCode()
            ,Counter.counter.get().hashCode()
            ,Counter.counter.get().toString());
        }

        public void set(String string) {
            Counter.counter.set(new StringBuilder(string));
            logger.info("threadName = {} , threadLocal hashcode = {}  , Instance hashcode = {} , value = {}"
                    ,Thread.currentThread().getName()
                    ,Counter.counter.hashCode()
                    ,Counter.counter.get().hashCode()
                    ,Counter.counter.get().toString());
        }

    }

    private static class Counter {
        private static ThreadLocal<StringBuilder> counter = new ThreadLocal<StringBuilder>(){
            @Override
            protected StringBuilder initialValue() {
                return new StringBuilder();
            }
        };

    }

}
