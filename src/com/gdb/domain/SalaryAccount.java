package main.java.com.gdb.domain;

import main.java.com.gdb.exceptions.*;

public class SalaryAccount extends AbstractAccount {
    private int inactiveMonths;
    private String employerName;

    public SalaryAccount(String accountNumber, String name, int age, double balance, String status, String pin, String employerName) {
        super(accountNumber, name, age, balance, "SALARY", status, pin);
        this.employerName = employerName;
        this.inactiveMonths = 0;
    }
    public void processDebit(double amount) throws AccountException, InsufficientBalanceException {
        if(amount>balance) throw new InsufficientBalanceException("Insufficient funds in Salary account");
    }
    public String getEmployerName() { return employerName; }
    public int getInactiveMonths() { return inactiveMonths; }
    public void incrementInactiveMonths() { this.inactiveMonths++; }
}
