package kg.attractor.online_quiz_platform.controller;

import jakarta.validation.Valid;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.dto.QuizShowDto;
import kg.attractor.online_quiz_platform.dto.UserDto;
import kg.attractor.online_quiz_platform.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping
    public List<QuizShowDto> getQuizzes() {
        return quizService.getQuizzes();
    }

    @PostMapping
    public HttpStatus createQuiz(@RequestBody @Valid QuizDto quizDto, Authentication auth) {
        quizService.createQuizAndReturnId(quizDto, auth);
        return HttpStatus.OK;
    }

    @GetMapping("{id}")
    public ResponseEntity<QuizShowDto> getResumeById(@PathVariable int id, Authentication auth) {
        return ResponseEntity.ok(quizService.getQuizById(id, auth));
    }

    @PostMapping("{id}/solve")
    public HttpStatus sendSolution(@RequestBody List<Integer> solutions, @PathVariable int id, Authentication auth) {
        quizService.sendSolution(solutions, id, auth);
        return HttpStatus.OK;
    }
}
