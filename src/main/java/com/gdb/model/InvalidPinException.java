package main.java.com.gdb.model;

public class InvalidPinException extends AccountException{
    public InvalidPinException(String message){
        super(message);
    }
}
