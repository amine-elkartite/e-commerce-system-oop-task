package exception;

public class CustomerNotMatchException extends RuntimeException{

    public CustomerNotMatchException(String message) {
        super(message);
    }
}
