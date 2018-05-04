package com.example.thread.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableTest {

    public static void main(String[] args) throws Exception {

        ExecutorService service = Executors.newSingleThreadExecutor();

        // Future与Callable中的泛型，就是返回值的类型
        Future<String> future = service.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "ghbaqi";
            }
        });

        System.out.println("1111");
        String name = future.get();     // 这里会被阻塞
        System.out.println("name = "+name);
        service.shutdown();

    }
}
