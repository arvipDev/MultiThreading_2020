package multiplethreads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CDLatch implements Runnable{
    private CountDownLatch cdl;
    CDLatch (CountDownLatch latch) {
        this.cdl = latch;
    }

    @Override
    public void run() {
        System.out.println("Starting..." + cdl.getCount());
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cdl.countDown();
    }
}

class process {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(6);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 6; i++){
            executor.submit(new CDLatch(latch));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed.");
        executor.shutdown();
    }
}
