package com.andrew.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Do doSomething = new Do();
        FutureTask<Integer> futureTask = new FutureTask<>(doSomething);
        new Thread(futureTask, "A").start();
        Integer result = futureTask.get();
        System.out.println(100 + result);

    }

}

class Do implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        return 1024;
    }
}
