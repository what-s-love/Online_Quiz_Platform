package kg.attractor.online_quiz_platform.controller;

import jakarta.validation.Valid;
import kg.attractor.online_quiz_platform.dto.AnswerListDto;
import kg.attractor.online_quiz_platform.dto.QuizCreateDto;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.dto.QuizReviewsDto;
import kg.attractor.online_quiz_platform.dto.QuizShowDto;
import kg.attractor.online_quiz_platform.dto.QuizShowListDto;
import kg.attractor.online_quiz_platform.dto.QuizSingleShowDto;
import kg.attractor.online_quiz_platform.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping
    public HttpStatus createQuiz(@RequestBody @Valid QuizCreateDto quizDto, Authentication auth) {
        quizService.createQuiz(quizDto, auth);
        return HttpStatus.OK;
    }

    @GetMapping
    public List<QuizShowListDto> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizSingleShowDto> getQuizById(@PathVariable Integer id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @PostMapping("{quizId}/solve")
    public HttpStatus sendSolution(@PathVariable int quizId, @RequestBody @Valid AnswerListDto answerListDto, Authentication auth) {
        quizService.solveQuiz(quizId, answerListDto, auth);
        return HttpStatus.OK;
    }

    @PostMapping("{quizId}/rate")
    public HttpStatus addVote(@RequestBody QuizReviewsDto quizReviewsDto, @PathVariable Long quizId){
        quizService.addVoteToQuiz(quizReviewsDto, quizId);
        return HttpStatus.OK;
    }
}
