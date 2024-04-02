package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Leaderboard {
    private String name;
    private Integer quizId;
    private double score;
}
