package com.example.thread.aqs.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTaskExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService service = Executors.newCachedThreadPool();

        Callable<Integer> callable = () -> {
            Thread.sleep(3000);
            return 100;
        };

        FutureTask<Integer> futureTask = new FutureTask<>(callable);

        service.submit(futureTask);

        System.out.println("do something in main");

        System.out.println("res = "+futureTask.get());

    }
}
