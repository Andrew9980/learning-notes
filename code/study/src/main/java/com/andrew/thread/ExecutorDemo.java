package com.andrew.thread;

import java.util.concurrent.*;

public class ExecutorDemo {

    public static void main(String[] args) {
//        ExecutorService executor = Executors.newCachedThreadPool();
//        Executors.newSingleThreadExecutor();
//        Executors.newFixedThreadPool(5);
        int threads = Runtime.getRuntime().availableProcessors()*10;
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(2,threads,1L, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 50; i++) {
            threadPoolExecutor.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + " Hello");
            });
        }
        threadPoolExecutor.shutdown();
    }

}
