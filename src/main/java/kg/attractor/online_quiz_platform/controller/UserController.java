package kg.attractor.online_quiz_platform.controller;

import kg.attractor.online_quiz_platform.dto.LeaderboardDto;
import kg.attractor.online_quiz_platform.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final QuizService quizService;

    @GetMapping("{userId}/statistics")
    public String getResultsByUserId(@PathVariable Long userId){
        return quizService.getResultByUserId(userId);
    }

    @GetMapping("leaderboard")
    public List<LeaderboardDto> getLeaderboard(){
        return quizService.getLeaderboard();
    }
}
