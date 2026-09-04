package main.java.com.gdb.model;

public class Account {
    private final int accountNumber;
    private String name;
    private int age;
    private double balance;
    private final String accountType;
    private  String status;
    public final double minimumBalance = 500;
    private Integer PIN=null;
    public Account(int accountNumber, String name, int age, double deposit, String accountType){
        this.accountNumber=accountNumber;
        this.accountType=accountType;
        this.age=age;
        this.name=name;
        if(deposit<minimumBalance){
            deposit=minimumBalance;
        }
        this.balance=deposit;
        this.status="Active";
    }
    public double getBalance(){
        return balance;
    }

    public boolean deposit(double amount){
        if(this.status.equals("Inactive")){
            return false;
        }
        else if(amount <= 0 ){
            return false;
        }
        balance+=amount;
        return true;
    }
    public boolean withdraw(double amount){
        if(this.status.equals("Inactive")) return false;
        else if(amount <= 0 || amount>balance) return false;
        else if(balance-amount < minimumBalance) return false;
        else if(this.PIN==null) return false;
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
    public boolean closeAccount(){
        if(this.status.equals("Inactive")){
            return false;
        }
        this.status="Inactive";
        return true;
    }
    public boolean reopenAccount(){
        this.status="Active";
        return true;
    }
    public boolean setPIN(String pin){
        int pinLength = pin.length();
        if(pinLength>4){
            return false;
        }
        try{
            this.PIN = Integer.parseInt(pin); //convert String to primitive int.
        }catch (NumberFormatException e){
            return false;
        }
        return true;
    }
    public boolean verifyPin(int pin){
        return this.PIN != null && this.PIN == pin;
    }
    public boolean hasPin(){
        return PIN != null;
    }
}


