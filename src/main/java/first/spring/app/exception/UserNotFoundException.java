package first.spring.app.exception;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message){
        super(message);
    }

}
