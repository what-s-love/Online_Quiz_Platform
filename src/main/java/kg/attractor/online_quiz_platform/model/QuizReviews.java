package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizReviews {
    private Long id;
    private Long quizId;
    private int estimation;
    private int numberOfVotes;
}
