package startingthreads;

public class RunnerTwo implements Runnable {
    @Override
    public void run() {
        for(int i = 0; i < 10; i++){
            System.out.println("Thread " + i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new RunnerTwo());
        t1.start();
        Thread t2 = new Thread(new RunnerTwo());
        t2.start();
    }
}
