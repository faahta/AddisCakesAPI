package et.addis.home_cakes.util.exception;

/**
 * Created by Fassil on 11/08/20.
 */
public class UserAlreadyExistsException extends Exception {
    private String message;

    public UserAlreadyExistsException(String message) {
        super();
        this.message = message;
    }
}
