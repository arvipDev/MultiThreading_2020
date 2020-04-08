package multiplethreads;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentLock {
    public static void main(String[] args) {

        ProcessFour process = new ProcessFour();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.threadOne();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.threadTwo();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            process.print();
        }
    }
}

class ProcessFour {

    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private void increment () {
        for (int i = 0; i < 1000; i++)
            count++;
    }

    void print () {
        System.out.println(count);
    }

    void threadOne () throws InterruptedException {
        lock.lock();
        System.out.println("threadOne acquired the lock and is waiting");
        condition.await();
        System.out.println("threadOne woke up.");
        try{
            increment();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            lock.unlock();
            System.out.println("threadOne released the lock.");
        }
    }

    void threadTwo () throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();
        System.out.println("threadTwo acquired the lock.");
        System.out.println("Hit enter to continue");
        new Scanner(System.in).nextLine();
        System.out.println("signal sent to threadTwo to wake up, but lock not released yet");
        condition.signal();
        try{
            increment();
        } finally {
            lock.unlock();
            System.out.println("threadTwo released the lock.");
            System.out.println("Size of counter: " + count);

        }
    }
}
