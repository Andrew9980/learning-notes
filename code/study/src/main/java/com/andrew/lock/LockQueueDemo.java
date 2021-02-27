package com.andrew.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 按照顺序从
 * A线程打印5次，B线程打印10次，C线程打印15次 依次循环
 * 使用Condition来分别唤醒线程执行
 */
public class LockQueueDemo {

    private Lock lock = new ReentrantLock();
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();
    private Condition conditionC = lock.newCondition();

    private volatile String sign = "A";

    public void print5() {
        try {
            lock.lock();
            while (!sign.equals("A")) {
                conditionA.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            sign = "B";
            conditionB.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {
        try {
            lock.lock();
            while (!sign.equals("B")) {
                conditionB.await();
            }
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            sign = "C";
            conditionC.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void print15() {
        try {
            lock.lock();
            while (!sign.equals("C")) {
                conditionC.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            }
            sign = "A";
            conditionA.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockQueueDemo demo = new LockQueueDemo();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                demo.print5();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                demo.print10();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                demo.print15();
            }
        }, "C").start();
    }


}
