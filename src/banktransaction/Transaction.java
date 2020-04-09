package banktransaction;

public class Transaction {


    int transfer (int amount, Account ac1, Account ac2){
        if(ac1.getBalance() < amount) return -1;
        ac1.setBalance(ac1.getBalance() - amount);
        ac2.setBalance(ac2.getBalance() + amount);
        // can use deposit and withdraw methods.
        return ac1.getBalance();
    }

    public int deposit (int amount, Account ac){
        ac.setBalance(ac.getBalance() + amount);
        return ac.getBalance();
    }

    public int withdraw (int amount, Account ac){
        if(ac.getBalance() < amount) return -1;
        ac.setBalance(ac.getBalance() - amount);
        return ac.getBalance();
    }

    public int getBalance (Account account) {
        return account.getBalance();
    }
}
