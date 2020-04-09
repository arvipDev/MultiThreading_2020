package generalmultithreading;

import java.util.Random;

public class InterruptsExample {

    public static void main(String[] args) {
        Random ran = new Random();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Running...");
                for (int i = 0; i < 1E5; i++){
                    System.out.println("Random number: " + ran.nextInt(1000));
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Thread interrupted at item : "  + i);
                        break;
                    }
                }
                System.out.println("Finished.");
            }
        });

        t1.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        t1.interrupt();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
