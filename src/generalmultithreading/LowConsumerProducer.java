package generalmultithreading;

import java.util.LinkedList;
import java.util.Random;

public class LowConsumerProducer {

    public static void main(String[] args) {
        ProcessorTwo process = new ProcessorTwo();
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

class ProcessorTwo {

    LinkedList<Integer> buffer = new LinkedList<Integer>();
    Random number = new Random();
    final int SIZE = 10;
    final Object lock = new Object();


    void producer () {
        int value;
        while (true){
            synchronized (lock) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                value = number.nextInt(100);
                buffer.add(value);
                System.out.println("producer in action: " + value + " ;list size: " + buffer.size());
                while (buffer.size() == SIZE){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    void consumer () {
        int value;
        while(true) {
            synchronized (lock) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(buffer.size() != 0){
                    value = buffer.removeFirst();
                    System.out.println("consumer in action: "  + value + " ;list size: " + buffer.size());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else lock.notify();
            }

        }
    }

}
