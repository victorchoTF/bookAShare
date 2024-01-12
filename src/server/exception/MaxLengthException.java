package server.exception;

public class MaxLengthException extends Exception{
    public MaxLengthException(String field, int length){
        super(field + " must be no more than " + length + " characters long!");
    }
}
