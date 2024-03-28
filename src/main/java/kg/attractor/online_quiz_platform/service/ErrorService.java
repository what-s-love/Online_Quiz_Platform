package kg.attractor.online_quiz_platform.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ErrorService{
    public kg.attractor.demo.exception.ErrorResponseBody makeResponse(Exception exception, int code) {
        String message = exception.getMessage();
        return kg.attractor.demo.exception.ErrorResponseBody.builder()
                .title(message)
                .reasons(List.of(message))
                .build();
    }

    public kg.attractor.demo.exception.ErrorResponseBody makeResponse(BindingResult exception) {
        List<String> errors = new ArrayList<>();

        for (ObjectError error : exception.getAllErrors()){
            errors.add(error.getDefaultMessage());
        }
        return kg.attractor.demo.exception.ErrorResponseBody.builder()
                .title("Validation error")
                .reasons(errors)
                .build();
    }
}
