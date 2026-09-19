package main.java.com.gdb.model;

public class InsufficientBalanceException extends AccountException {
    public InsufficientBalanceException(String message){
        super(message);
    }
}
