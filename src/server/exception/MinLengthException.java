package server.exception;

public class MinLengthException extends Exception{
    public MinLengthException(String field, int length){
        super(field + " must be no less than " + length + " characters long!");
    }
}
