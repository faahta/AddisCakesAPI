package et.addis.home_cakes.util.exception;

/**
 * Created by Fassil on 14/09/20.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
