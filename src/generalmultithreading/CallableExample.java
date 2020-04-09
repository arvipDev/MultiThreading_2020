package generalmultithreading;

import java.util.Random;
import java.util.concurrent.*;

public class CallableExample {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<Integer> returnedValue = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Random random = new Random();
                int duration = random.nextInt(100);
/*                if(duration < 50) {
                    throw new IOException("Throwing an IO exception: ");
                }*/
                System.out.println("Starting...");
                Thread.sleep(duration);
                System.out.println("Finished.");
                return duration;
            }
        });
        executor.shutdown();
        try {
            System.out.println(returnedValue.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
