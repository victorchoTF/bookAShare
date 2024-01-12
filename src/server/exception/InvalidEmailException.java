package server.exception;

public class InvalidEmailException extends Exception{
    public InvalidEmailException(String email) {
        super(email + " is not a valid email!");
    }
}
