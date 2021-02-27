package com.andrew.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {
    private AtomicReference<Thread> atomicReference = new AtomicReference<>();
    private void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t" + " come in ");

        // 期望当前没有锁，将自己放入到队列中，否则就循环获取
        while (!atomicReference.compareAndSet(null, thread)) {
            System.out.println(thread.getName() + " wait ");
        }
    }
    private void myUnLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + " free lock ");
    }
    public static void main(String[] args) throws InterruptedException {
        SpinLockDemo demo = new SpinLockDemo();
        new Thread(() -> {
            demo.myLock();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            demo.myUnLock();
        }, "t1").start();
        // 主线程等待
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            demo.myLock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            demo.myUnLock();
        }, "t2").start();
    }
}
