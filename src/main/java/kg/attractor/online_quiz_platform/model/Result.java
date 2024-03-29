package kg.attractor.online_quiz_platform.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private int id;
    private int userId;
    private int quizId;
    //0-100
    private double score;
}
