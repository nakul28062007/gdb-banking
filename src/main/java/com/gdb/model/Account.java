package main.java.com.gdb.model;

public class Account {
    private final int accountNumber;
    private String name;
    private int age;
    private double balance;
    private final String accountType;
    private final String status;
    public Account(int accountNumber, String name, int age, double initialBalance, String accountType){
        this.accountNumber=accountNumber;
        this.accountType=accountType;
        this.age=age;
        this.name=name;
        this.balance=initialBalance;
        this.status="Active";
    }
    public double getBalance(){
        return balance;
    }

    public boolean deposit(double amount){
        if(amount <= 0 ){
            return false;
        }
        balance+=amount;
        return true;
    }
    public boolean withdraw(double amount){
        if(amount <= 0 || amount>balance){
            return false;
        }
        else balance -= amount;
        return true;
    }
    public int getAccountNumber(){
        return accountNumber;
    }
    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public String getAccountType(){
        return accountType;
    }
    public String getStatus(){
        return status;
    }
    void setName(String name){
        this.name = name;
    }
    void setAge(int age){
        this.age = age;
    }
}


