package kg.attractor.demo.exception.handler;

import kg.attractor.demo.exception.UserNotFoundException;
import kg.attractor.demo.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorService errorService;

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElement(NoSuchElementException exception) {
        return new ResponseEntity<>(errorService.makeResponse(exception, 404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> noSuchUser(UserNotFoundException exception) {
        return new ResponseEntity<>(errorService.makeResponse(exception, 404), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity validationHandler(MethodArgumentNotValidException exception) {
        return new ResponseEntity(errorService.makeResponse(exception), HttpStatus.BAD_REQUEST);
    }
}
