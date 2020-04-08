package multiplethreads;

public class WaitNotify {
    public static void main(String[] args) {
        Processor process = new Processor();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.producer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                process.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
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

class Processor {
    void producer () throws InterruptedException {
        synchronized (this){
            System.out.println("Started initial thread...");
            wait();
            System.out.println("resumed execution of initial thread...");
        }
    }
    void consumer () throws InterruptedException {
        synchronized (this) {
            Thread.sleep(2000);
            System.out.println("started new thread execution...");
            notify();
            System.out.println("Completing execution of thread after notify() was called...");
            Thread.sleep(3000);
        }
    }
}