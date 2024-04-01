package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {
    private Integer id;
    private Integer quizId;
    private String questionText;
}
