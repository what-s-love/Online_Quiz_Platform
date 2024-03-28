package kg.attractor.online_quiz_platform.exception;

public class UserAlreadyExists extends Exception{
    public UserAlreadyExists() {
    }

    public UserAlreadyExists(String message) {
        super(message);
    }
}
