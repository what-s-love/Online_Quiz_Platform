package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {
    private int id;
    private int quizId;
    private String questionText;
}
