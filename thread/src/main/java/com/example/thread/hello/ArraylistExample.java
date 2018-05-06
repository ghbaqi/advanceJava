package com.example.thread.hello;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ArraylistExample {

    private static int threads = 200;
    private static int maxThreads = 5000;

//    static List<Integer> list = new ArrayList<>();
//    static List<Integer> list = Collections.synchronizedList(new ArrayList<>());
//    static Vector<Integer> list = new Vector<>();                                 // 线程安全

    static List list = new CopyOnWriteArrayList();


    public static void main(String[] args) throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        final  Semaphore semaphore = new Semaphore(threads);
        CountDownLatch countDownLatch = new CountDownLatch(5000);

        for (int i = 0; i < maxThreads; i++) {
            int finalI = i;
            service.execute(() -> {
                try {
                    semaphore.acquire();
                    excute(finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                    countDownLatch.countDown();
                }

            });
        }

        System.out.println("1111");
        countDownLatch.await();
        System.out.println("2222");
        System.out.println("list.size = "+list.size());
        service.shutdown();


    }

    private static void excute(int finalI) {
        try {
            Thread.sleep(80);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list.add(finalI);
    }
}
