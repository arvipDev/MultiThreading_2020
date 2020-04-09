package generalmultithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiTh {



    private List<Integer> list1 = new ArrayList<Integer>();
    private List<Integer> list2 = new ArrayList<Integer>();
    private Random random = new Random();
    final Object lock1 = new Object(); // providing separate locks to synchronized block in stageOne()
    final Object lock2 = new Object(); // providing separate locks to synchronized block in stageTwo()

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                new MultiTh().process();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                new MultiTh().process();
            }
        });

        t1.start();
        t2.start();

        try {
            t2.join();
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("Duration: " + (end - start));
    }

    private void stageOne () {
        synchronized (lock1){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }

    }

    private void stageTwo () {
        synchronized (lock2){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }
    }

    private void process() {
        for(int i = 0; i < 1000; i++){
            stageOne();
            stageTwo();
        }
        System.out.println("list1 " + list1.size());
        System.out.println("list2 " + list2.size());

    }
}
