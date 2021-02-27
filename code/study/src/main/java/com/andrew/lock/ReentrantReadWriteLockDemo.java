package com.andrew.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock 可重入读写锁
 * 独占锁：读写，写读，写写
 * 共享锁：读读
 */
public class ReentrantReadWriteLockDemo {
    private volatile Map<String, Object> cacheMap = new HashMap<>();
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    public void put(String key, Object value) {
        try {
            reentrantReadWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + "\t" + "start write");
            TimeUnit.MICROSECONDS.sleep(100);
            cacheMap.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t" + "write finish");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }
    public void get(String key) {
        try {
            reentrantReadWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + "\t" + "start read");
            try {
                TimeUnit.MICROSECONDS.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "read finish: " + cacheMap.get(key));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLockDemo demo = new ReentrantReadWriteLockDemo();
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                demo.put(Thread.currentThread().getName(), UUID.randomUUID().toString());
            }, String.valueOf(i)).start();
        }
        TimeUnit.SECONDS.sleep(1);
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                demo.get(Thread.currentThread().getName());
            }, String.valueOf(i)).start();
        }
    }
}
