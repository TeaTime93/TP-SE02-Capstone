package SE02.Capstone.exceptions;

public class UserNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 6374370427272120098L;

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

}

