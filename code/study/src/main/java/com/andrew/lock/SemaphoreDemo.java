package com.andrew.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


/**
 * Semaphore 用来控制同时访问特定资源的线程数量，达到线程数量后，其他线程只能等待运行线程释放。
 * 类似于抢车位，总共3个车位，10辆车争抢。同时是能有3个车使用，剩余的车只能等待车位释放才能停车。
 *
 * Semaphore可以用于做流量控制，特别公用资源有限的应用场景，比如数据库连接。
 * 假如有一个需求，要读取几万个文件的数据，因为都是IO密集型任务，我们可以启动几十个线程并发的读取，但是如果读到内存后，
 * 还需要存储到数据库中，而数据库的连接数只有10个，这时我们必须控制只有十个线程同时获取数据库连接保存数据，否则会报错无法获取数据库连接
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        // 最大允许3个线程并发
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    // 请求线程资源
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 线程执行");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放占用资源
                    System.out.println(Thread.currentThread().getName() + " 线程完成");
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
