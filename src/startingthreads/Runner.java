package startingthreads;

public class Runner extends Thread {
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
        Runner run = new Runner();
        run.start();
        Runner run2 = new Runner();
        run2.start();

        //calling run method in the Runner class will call the method on the main thread
        //calling start method on the Runner class will ask the class to look for run() and will call the method on a special thread.

    }
}
