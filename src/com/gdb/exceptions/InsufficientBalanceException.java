package main.java.com.gdb.exceptions;

public class InsufficientBalanceException extends AccountException {
    public InsufficientBalanceException(String message){
        super(message);
    }
}
