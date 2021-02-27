package com.andrew.lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {


    public static void main(String[] args) {
        ReentrantLockDemo test = new ReentrantLockDemo();
        for (int i = 0; i < 2; i++) {
            new Thread(test::method03, String.valueOf(i)).start();
        }

//        new Thread(test::method03, "t2").start();
    }

    public synchronized void method01() {
        method02();
    }

    public synchronized void method02() {

    }
    // lock作为类变量，保证锁对象是类的对象，获取锁是同一个对象锁
    ReentrantLock lock = new ReentrantLock();
    public void method03() {
        lock.lock();
        System.out.println(Thread.currentThread().getName() + "\t" + "invoke method03");
        method04();
        lock.unlock();
    }

    public void method04() {
        lock.lock();
        // 业务
        System.out.println(Thread.currentThread().getName() + "\t" + "invoke method04");
        lock.unlock();
    }

}
