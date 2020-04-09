package semaphoreExample;

import java.util.concurrent.Semaphore;

public class Connection {

    private static Connection connection = new Connection();
    private Semaphore semLock = new Semaphore(5, true);
    private int counter = 0;

    private Connection () {}

    public static Connection getInstance () {
        return connection;
    }

    public void connect () {
        try {
            semLock.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            wire();
        } finally {
            semLock.release();
        }

    }

    public void wire () {
        synchronized (this) {
            counter++;
            System.out.println("Connection number : " + counter);
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (this) {
            counter--;
            System.out.println("Connection number : " + counter);
        }

    }

}
