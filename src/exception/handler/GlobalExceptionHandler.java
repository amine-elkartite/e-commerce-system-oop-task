package exception.handler;

import exception.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalExceptionHandler {

    public static void handle(Exception e) {
        Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

        if (e instanceof CustomerNotMatchException) {
            logger.log(Level.SEVERE, e.getMessage());
        } else if (e instanceof ProductNotFoundException) {
            logger.log(Level.WARNING, e.getMessage());
        } else if (e instanceof CartEmptyException) {
            logger.log(Level.WARNING, e.getMessage());
        } else if (e instanceof DuplicateProductException) {
            logger.log(Level.WARNING, e.getMessage());
        } else if (e instanceof InsufficientBalanceException) {
            logger.log(Level.WARNING, e.getMessage());
        } else if (e instanceof InsufficientQuantityException) {
            logger.log(Level.WARNING, e.getMessage());
        } else if (e instanceof ProductExpiredException) {
            logger.log(Level.WARNING, e.getMessage());
        } else {
            logger.log(Level.WARNING, "An unexpected exception occurred", e);
        }
    }
}
