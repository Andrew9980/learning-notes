package com.andrew;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) throws InterruptedException {

//        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(3);
//        System.out.println(queue.add(1)); // 添加元素
//        System.out.println(queue.add(2));
//        System.out.println(queue.add(3));
//        System.out.println(queue.element()); // 选取队列第一个元素 队列为空抛出异常
//        System.out.println(queue.add(4)); // 此时添加时会抛出异常

//        System.out.println(queue.offer(1)); // 添加元素
//        System.out.println(queue.offer(2));
//        System.out.println(queue.offer(3));
//        System.out.println(queue.offer(4)); // 无法插入，但不会抛出异常
//        System.out.println(queue.peek()); // 返回第一个元素 队列为空 返回null

        BlockingQueue<Integer> queue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t put 1");
                queue.put(1);
                System.out.println(Thread.currentThread().getName() + "\t put 2");
                queue.put(2);
                System.out.println(Thread.currentThread().getName() + "\t put 3");
                queue.put(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

        TimeUnit.SECONDS.sleep(2);

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "\t take " + queue.take());
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "\t take " + queue.take());
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "\t take " + queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();

    }
}
