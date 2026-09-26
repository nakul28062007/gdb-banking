package main.java.com.gdb.domain;
import main.java.com.gdb.exceptions.*;

public class CurrentAccount extends AbstractAccount{
    private double overdraftLimit;
    public CurrentAccount(String accountNumber, String name, int age, double balance, String status, String pin, double overdraftLimit) {
        super(accountNumber, name, age, balance, "CURRENT", status, pin);
        this.overdraftLimit = overdraftLimit;
    }
    public void processDebit(double amount) throws AccountException, InsufficientBalanceException {
        if(amount>(balance+overdraftLimit)) throw new InsufficientBalanceException("Overdraft limit exceeded");
    }
    public double getOverdraftLimit() { return overdraftLimit; }
    public void setOverdraftLimit(double overdraftLimit) { this.overdraftLimit = overdraftLimit; }
}

