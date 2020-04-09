package generalmultithreading;

public class Sync {

    // using synchronized key word to issue lock to a thread so that only one thread has access at a time.
    // using thread.join() method to make the other thread wait till the current thread finishes execution.

    private static int count;
    public static void main(String[] args) {
        Sync sync = new Sync();
        Thread t1 = new Thread(() -> {
            for(int i = 1; i <= 2000; i++){
                sync.printer();
            }
        });
        Thread t2 = new Thread(() -> {
            for(int i = 1; i <= 2000; i++){
                sync.printer();
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
        System.out.println(count);
    }

    private synchronized void printer(){
        count++;
    }
}
