package com.andrew;


import java.util.concurrent.TimeUnit;

/**
 * JMM规范：
 * Java内存模型规定所有变量都存储在主内存（堆），主内存是共享内存区域，所有线程都可以访问，但是线程对变量的操作必须
 * 在工作内存中进行，分为3步，1：从主内存中读取变量值到工作内存（栈） 2：对变量值操作 3：将变量值写回主内存。这3步
 * 不是原子操作，所以volatile不能保证原子性。加了volatile关键字修饰，java会强制线程操作时先从主内存中读取变量值
 *
 * volatile 特性：
 * 1. 内存可见性
 * 2. 不保证原子性
 * 3. 禁止指令冲排序
 */
public class VolatileDemo {

    volatile int number = 0;

    public void add(int num) {
        number += num;
    }

    public void addPlusPlus() {
        number++;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileDemo demo = new VolatileDemo();
//        validMemoryWatch(demo);
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    demo.addPlusPlus();
                }
            }).start();
        }

        while (Thread.activeCount() > 2) {
//            Thread.currentThread().join(); // 使得一个线程在另一个线程结束后再执行
            Thread.yield(); // 将线程的状态从运行设置为可运行状态，让相同优先级的线程执行，但是cpu不能保证yield有效，因为可运行状态的线程有可能再次被选中执行
        }

        System.out.println(Thread.currentThread().getName() + " : final number value: " + demo.number);
    }

    /**
     * 验证内存可见性
     * @param demo
     */
    private static void validMemoryWatch(VolatileDemo demo) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                demo.add(10);
                System.out.println(Thread.currentThread().getName() + " : update number value : " + demo.number);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        while (demo.number == 0) {

        }
    }

}
