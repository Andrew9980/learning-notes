package com.andrew.thread;


import java.util.concurrent.TimeUnit;

class TestLock implements Runnable {
    private String a;
    private String b;

    public TestLock(String a, String b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void run() {
        synchronized (a) {
            System.out.println(Thread.currentThread().getName() + "\t lock " + a + " wait B");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (b) {
                System.out.println(Thread.currentThread().getName() + "\t lock" + b + "wait A");
            }
        }
    }
}
public class DeadLockDemo {

    public static void main(String[] args) {

        String a = "a";
        String b = "b";

        new Thread(new TestLock(a, b), "A").start();
        new Thread(new TestLock(b, a), "B").start();
    }

}
