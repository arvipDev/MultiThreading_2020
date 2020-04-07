package multiplethreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorSvc implements Runnable {

    private int id;

    @Override
    public void run() {
        System.out.println("Started... " + id);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed... " + id);
    }

    public ExecutorSvc (int id) {
        this.id = id;
    }
}

class TestExecSvc {
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        for(int i = 1; i < 6; i++){
            service.submit(new ExecutorSvc(i));
        }

        service.shutdown();

        try {
            service.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("All tasks are complete: ");
    }
}
