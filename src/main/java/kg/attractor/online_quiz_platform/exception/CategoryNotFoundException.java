package kg.attractor.online_quiz_platform.exception;

public class CategoryNotFoundException extends Exception{
    public CategoryNotFoundException() {
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}
