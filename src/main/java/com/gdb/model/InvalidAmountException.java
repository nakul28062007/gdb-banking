package main.java.com.gdb.model;

public class InvalidAmountException extends AccountException{
    public InvalidAmountException(String message){
        super(message);
    }
}
