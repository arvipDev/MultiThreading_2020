package multiplethreads;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class ProducerConsumer {

    private BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);
    private Random randomNumber = new Random();

    void producer() throws InterruptedException {
        while (true){
            Thread.sleep(100);
            int value = new Random().nextInt(100);
            queue.put(value);
            System.out.println("Producer activated: Add to Queue - " + value + " updated queue size - " + queue.size());
        }
    }
    void consumer() throws InterruptedException {
        while (true){
            Thread.sleep(250);
            if (new Random().nextInt(10) != 0){
                int value = queue.take();
                System.out.println("Consumer activated: Take from Queue - " + value + " updated queue size - " + queue.size());
            }
        }
    }
}

class Process {
    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consumer();
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
        }
    }
}
