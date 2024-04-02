package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizReviews {
    private Integer id;
    private Integer quizId;
    private Integer userId;
    private Integer estimation;
}
