package banktransaction;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AccountProcessor {

    private Account account1 = new Account(1846792);
    private Account account2 = new Account(2687942);
    private Transaction transact = new Transaction();
    private Random random = new Random();
    private Thread t2;
    private Thread t1;
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AccountProcessor process = new AccountProcessor();
            process.process();
        }
    }

    private void threadOne(){

        //lock1.lock();
        //lock2.lock();
        acquireLocks(lock1, lock2);
        try {
            t1 = new Thread(() -> {
                for (int i = 0; i < 100; i++){
                    System.out.println("Account 2 balance: " + account2.getBalance());
                    transact.transfer(random.nextInt(100), account1, account2);
                }
            });
        } finally {
            lock1.unlock();
            lock2.unlock();
            System.out.println("Account 1 final balance: " + account1.getBalance());
        }
        System.out.println("Total amount in account 1 " + account1.getBalance());
    }

    private void threadTwo(){

        //lock1.lock();
        //lock2.lock();
        acquireLocks(lock1, lock2);
        try {
            t2 = new Thread(() -> {
                for (int i = 0; i < 100; i++){
                    System.out.println("Account 1 balance: " + account1.getBalance());
                    transact.transfer(random.nextInt(100), account2, account1);
                }
            });
        } finally {
            lock1.unlock();
            lock2.unlock();
            System.out.println("Account 2 final balance: " + account2.getBalance());
        }
        System.out.println("Total amount in account 2 " + account2.getBalance());
    }

    private void acquireLocks (Lock lock1, Lock lock2) {

        boolean firstLock = false;
        boolean secondLock = false;
        while (true) {
            try {
                firstLock = lock1.tryLock();
                secondLock = lock2.tryLock();
            } finally {
                if (firstLock && secondLock){
                    return;
                } else if (firstLock) lock1.unlock();
                else if (secondLock) lock2.unlock();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void process () {

        threadOne();
        threadTwo();

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int total = account1.getBalance() + account2.getBalance();
        System.out.println("Total amount in account 1 " + account1.getBalance());
        System.out.println("Total amount in account 2 " + account2.getBalance());
        System.out.println("Total amount " + total);
    }
}
