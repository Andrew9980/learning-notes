package com.andrew;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {

    public static void main(String[] args) {
        // ABA问题
//        AtomicReference<Integer> atomicReference = new AtomicReference<>(0);
//        new Thread(() -> {
//            boolean r = atomicReference.compareAndSet(0, 100);
//            System.out.println(Thread.currentThread().getName() + "\t" + "修改：" + r + "第一次修改值为: " + atomicReference.get());
//
//            boolean r1 = atomicReference.compareAndSet(100, 0);
//            System.out.println(Thread.currentThread().getName() + "\t" + "修改：" + r1 + "第二次修改值为: " + atomicReference.get());
//        }, "t1").start();
//        new Thread(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(1);
//                boolean r = atomicReference.compareAndSet(0, 200);
//                System.out.println(Thread.currentThread().getName() + "\t" + "修改：" + r + "第一次修改值为: " + atomicReference.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }}, "t2").start();

        // ABA解决: AtomicStampedReference提供原子类引用，使用Stamp的方式，在每一次修改时添加版本号或时间戳，类似于乐观锁
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(0, 1);
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t" + "第一次修改，门票为：" + stamp);
            boolean r = atomicStampedReference.compareAndSet(0, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t" + "修改：" + r + "第一次修改为: " + atomicStampedReference.getReference());
            System.out.println(Thread.currentThread().getName() + "\t" + "第二次修改，门票为：" + atomicStampedReference.getStamp());
            boolean r1 = atomicStampedReference.compareAndSet(100, 0, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t" + "修改：" + r1 + "第二次修改为: " + atomicStampedReference.getReference());
        }, "t3").start();
        new Thread(() -> {
            try {
                int stamp = atomicStampedReference.getStamp();
                System.out.println(Thread.currentThread().getName() + "\t" + "第一次修改，门票为：" + stamp);
                TimeUnit.SECONDS.sleep(3);
                boolean r = atomicStampedReference.compareAndSet(0, 200, stamp, stamp + 1);
                System.out.println(Thread.currentThread().getName() + "\t" + "修改：" + r + "第一次修改为: " + atomicStampedReference.getReference());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t4").start();

    }
}
