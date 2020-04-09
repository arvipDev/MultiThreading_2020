package generalmultithreading;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProdConsReentrantLock {
    public static void main(String[] args) {
        ProcessThree process = new ProcessThree();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                process.producer();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                process.consumer();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ProcessThree {
    private Lock reentrantLock = new ReentrantLock();
    private Condition condiiton = reentrantLock.newCondition();
    private LinkedList<Integer> buffer = new LinkedList<Integer>();
    final int SIZE = 10;
    Random num = new Random();

    void producer () {
        reentrantLock.lock();
        System.out.println("Lock acquired by producer");
        int value;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            while(buffer.size() < SIZE){
                value = num.nextInt(100);
                buffer.add(value);
                System.out.println("producer in action: " + value + " ;list size: " + buffer.size());
            }
            condiiton.signal();
            System.out.println("Signal sent to consumer threads to wake up");
            reentrantLock.unlock();
            System.out.println("lock released by producer");
        }
    }

    void consumer () {
        reentrantLock.lock();
        System.out.println("Lock acquired by consumer");
        int value;
        try {
            //condiiton.await();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            while(buffer.size() != 0){
                value = buffer.removeFirst();
                System.out.println("consumer in action: " + value + " ;list size: " + buffer.size());
                condiiton.signal();
            }
            System.out.println("Signal sent to producer threads to wake up");
            reentrantLock.unlock();
            System.out.println("lock released by consumer");
        }
    }
}
