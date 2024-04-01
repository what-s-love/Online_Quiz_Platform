package kg.attractor.online_quiz_platform.exception;

public class QuizNotFoundException extends Exception{
    public QuizNotFoundException() {
    }

    public QuizNotFoundException(String message) {
        super(message);
    }
}
