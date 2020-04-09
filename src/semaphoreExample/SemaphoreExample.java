package semaphoreExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample {

    private Semaphore semLock = new Semaphore(1);
    private int counter;
    private Semaphore semLock2 = new Semaphore(10);

    public void semaTest() throws InterruptedException {
        System.out.println("semLock available permits: " + semLock.availablePermits());
        semLock.acquire();
        System.out.println("semLock available permits after acquire() call: " + semLock.availablePermits());
        semLock.release();
        System.out.println("semLock available permits after release() call: " + semLock.availablePermits());
        semLock.acquire();
        System.out.println("trying to acquire permit when there are " + semLock.availablePermits() + " permits");
        semLock.tryAcquire(5000, TimeUnit.MILLISECONDS);
        System.out.println("waited for 5 seconds for the permit. " + semLock.availablePermits());
        semLock.release();
        semLock.release();
        System.out.println("releasing more permits than what was initialized: " + semLock.availablePermits());
    }

    private void connect () throws InterruptedException {
        ExecutorService es1 = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++){
            es1.submit(new Runnable() {
                @Override
                public void run() {
                    Connection.getInstance().connect();
                }
            });
        }
        es1.shutdown();
        es1.awaitTermination(1, TimeUnit.DAYS);
    }



    public static void main(String[] args) throws InterruptedException {
        SemaphoreExample semaphore = new SemaphoreExample();
        semaphore.semaTest();

        System.out.println("Connection...");
        semaphore.connect();
    }
}
